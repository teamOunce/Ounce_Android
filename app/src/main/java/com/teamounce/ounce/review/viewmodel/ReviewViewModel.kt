package com.teamounce.ounce.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.ReviewRepository
import com.teamounce.ounce.review.model.ResponseReview
import com.teamounce.ounce.review.model.ResponseReviewInfo
import com.teamounce.ounce.review.model.ResponseSearch
import com.teamounce.ounce.review.model.ResponseTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val TAG_MAX_ENTRY = 3
    private val selectedTag = mutableListOf<String>()
    private val _tagList = MutableLiveData<List<ResponseTags.Data>>()
    val tagList: LiveData<List<ResponseTags.Data>>
        get() = _tagList
    private val _registerResult = MutableLiveData<ResponseReview>()
    val result: LiveData<ResponseReview>
        get() = _registerResult
    private val _reviewInfo = MutableLiveData<ResponseReviewInfo.Data>()
    val reviewInfo: LiveData<ResponseReviewInfo.Data>
        get() = _reviewInfo
    private var multiPartFile: MultipartBody.Part? = null
    private val _warningMessage = MutableLiveData<String>()
    val warningMessage: LiveData<String>
        get() = _warningMessage
    var preference = 0f
    var isTagsFull = false
    val memo = MutableLiveData<String>()
    var reviewIndex = -1
    private lateinit var emptyImage: MultipartBody.Part

    fun getTags() = viewModelScope.launch {
        runCatching { reviewRepository.getTags(OunceLocalRepository.catIndex) }
            .onSuccess { _tagList.value = it.data }
            .onFailure { Log.d("TAG", it.message.toString()) }
    }

    fun addTag(tag: String) {
        runCatching {
            require(selectedTag.size <= TAG_MAX_ENTRY) { "태그를 3개 이상 선택할 수 없습니다" }
        }.onSuccess {
            if (!selectedTag.contains(tag))
                selectedTag.add(tag)
            if (selectedTag.size == TAG_MAX_ENTRY) isTagsFull = true
            Log.d("TAG SUCCESS", selectedTag.toString())
        }.onFailure {
            _warningMessage.value = it.message
            if (selectedTag.size == TAG_MAX_ENTRY) isTagsFull = true
            Log.d("TAG FAILURE", selectedTag.toString())
        }
    }

    fun deleteTag(tag: String) {
        selectedTag.removeIf { it == tag }
        if (selectedTag.size <= TAG_MAX_ENTRY) isTagsFull = false
    }

    fun setImageFile(part: MultipartBody.Part) {
        multiPartFile = part
    }

    fun registerReview(productData: ResponseSearch.Data) {
        Log.e("Call register", "Call Register")
        viewModelScope.launch {
            runCatching {
                reviewRepository.registerReview(
                    body = providePartMap(productData),
                    image = multiPartFile ?: emptyImage
                )
            }.onSuccess {
                _registerResult.value = it
            }.onFailure {
                when(it) {
                    is HttpException -> {
                        Log.d("TAG", it.code().toString())
                    }
                }
                Log.d("TAG", it.stackTraceToString())
            }
        }
    }

    private fun providePartMap(productData: ResponseSearch.Data): HashMap<String, RequestBody> {
        val catIndex =
            OunceLocalRepository.catIndex.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val productIndex =
            productData.productIndex.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val preference =
            preference.toInt().toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val memo = (memo.value ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
        val tagList = _tagList.value!!.asSequence()
            .filter { selectedTag.contains("#${it.tag}") }
            .map { it.tag }
            .toList()
        val tagMap = hashMapOf<String, RequestBody>()
        repeat(TAG_MAX_ENTRY) {
            if (it <= tagList.size - 1) {
                tagMap["tag${it + 1}"] = tagList[it].toRequestBody("text/plain".toMediaTypeOrNull())
            } else {
                tagMap["tag${it + 1}"] = "".toRequestBody("text/plain".toMediaTypeOrNull())
            }
        }
        val partMap = hashMapOf(
            "catIndex" to catIndex,
            "productIndex" to productIndex,
            "preference" to preference,
            "memo" to memo,
        )
        partMap.putAll(tagMap)
        Log.d("TAG", partMap.toString())
        return partMap
    }

    private fun provideModifyPartMap(): HashMap<String, RequestBody> {
        val reviewIndex =
            this.reviewIndex.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val preference =
            preference.toInt().toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val memo = memo.value!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val tagList = _tagList.value!!.asSequence()
            .filter { selectedTag.contains(it.tag) }
            .map { it.tag }
            .toList()
        val tagMap = hashMapOf<String, RequestBody>()
        repeat(TAG_MAX_ENTRY) {
            if (it <= tagList.size - 1) {
                tagMap["tag${it + 1}"] = tagList[it].toRequestBody("text/plain".toMediaTypeOrNull())
            } else {
                tagMap["tag${it + 1}"] = "".toRequestBody("text/plain".toMediaTypeOrNull())
            }
        }
        val partMap = hashMapOf(
            "reviewIndex" to reviewIndex,
            "preference" to preference,
            "memo" to memo,
        )
        partMap.putAll(tagMap)
        return partMap
    }

    fun getReviewInfo(reviewIndex: Int) {
        viewModelScope.launch {
            runCatching { reviewRepository.getReviewIndo(reviewIndex) }
                .onSuccess { _reviewInfo.value = it.data }
                .onFailure { _warningMessage.value = "서버에서 리뷰 정보를 갖고 오는데 실패했습니다" }
        }
    }

    fun initTags(list: List<String>) {
        Log.d("TAG", list.toString())
        selectedTag.addAll(list)
    }

    fun isTagEntryFull(): Boolean = selectedTag.size > TAG_MAX_ENTRY

    fun isContained(tag: String): Boolean = selectedTag.contains(tag)

    fun modifyReview() {
        viewModelScope.launch {
            runCatching {
                reviewRepository.modifyReview(
                    body = provideModifyPartMap(),
                    image = multiPartFile ?: emptyImage
                )
            }.onSuccess {
                _registerResult.value = it
            }.onFailure {
                Log.d("TAG", it.stackTraceToString())
            }
        }
    }

    fun setEmptyImage(part: MultipartBody.Part) { emptyImage = part }
}