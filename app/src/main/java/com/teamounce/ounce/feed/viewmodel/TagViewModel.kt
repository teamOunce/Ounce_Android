package com.teamounce.ounce.feed.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.TagRepository
import com.teamounce.ounce.feed.model.RequestAddTag
import com.teamounce.ounce.review.model.ResponseTags
import com.teamounce.ounce.util.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagRepository: TagRepository
) : ViewModel() {
    private val _tagList = MutableLiveData<List<ResponseTags.Data>>(listOf())
    val tagList: LiveData<List<ResponseTags.Data>>
        get() = _tagList
    val tagQuery = MutableLiveData("")
    val isTagMax: LiveData<Boolean> = Transformations.map(tagList) { isTagMax(it.size) }
    val queryWordMax: LiveData<Boolean> = Transformations.map(tagQuery) { isQueryMax(it.length) }
    val isExistedTag: LiveData<Boolean> = Transformations.map(tagQuery) { isTagContained(it) }
    val isContainBlank: LiveData<Boolean> = Transformations.map(tagQuery) { it.contains(" ") }
    val isAddEnabled = MediatorLiveData<Boolean>().apply {
        addSourceList(isTagMax, queryWordMax, isExistedTag, isContainBlank) { isButtonEnabled() }
    }
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val TAG_MAX_STORE = 15
    private val QUERY_MAX_WORD = 6
    private val SUCCESS = 1

    init {
        viewModelScope.launch {
            runCatching { tagRepository.getTags(OunceLocalRepository.catIndex) }
                .onSuccess { _tagList.value = it.data }
                .onFailure { _errorMessage.value = "태그를 불러오는 데 실패했습니다" }
        }
    }

    fun addTag() {
        viewModelScope.launch {
            runCatching {
                tagRepository.addTag(
                    catIndex = OunceLocalRepository.catIndex,
                    body = RequestAddTag(tagQuery.value ?: "")
                )
            }.onSuccess {
                if (isAddSuccess(it.data)) { refreshTags() }
                else { _errorMessage.value = "태그를 저장하는 데 실패했습니다." }
            }.onFailure {
                _errorMessage.value = "태그를 저장하는 데 실패했습니다."
                Log.d("TAG", it.message.toString())
            }
        }
    }

    fun deleteTag(tagIndex: Int) {
        viewModelScope.launch {
            runCatching { tagRepository.deleteTag(tagIndex) }
                .onSuccess { refreshTags() }
                .onFailure { _errorMessage.value = "태그를 불러오는 데 실패했습니다" }
        }
    }


    private fun refreshTags() {
        viewModelScope.launch {
            runCatching { tagRepository.getTags(OunceLocalRepository.catIndex) }
                .onSuccess { _tagList.value = it.data }
                .onFailure { _errorMessage.value = "태그를 불러오는 데 실패했습니다" }
        }
    }

    private fun isTagMax(size: Int) = size >= TAG_MAX_STORE
    private fun isQueryMax(size: Int) = size > QUERY_MAX_WORD
    private fun isAddSuccess(flag: Int) = (flag == SUCCESS)
    private fun isTagContained(tag: String): Boolean {
        val tags = tagList.value?.map { it.tag }
        return tags!!.contains(tag)
    }

    private fun isButtonEnabled(): Boolean {
        return !isTagMax.value!! && !queryWordMax.value!! && !isExistedTag.value!! && !isContainBlank.value!!
    }
}