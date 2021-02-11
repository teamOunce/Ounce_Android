package com.teamounce.ounce.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.ReviewRepository
import com.teamounce.ounce.review.model.ResponseTags
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    private val _warningMessage = MutableLiveData<String>()
    val warningMessage: LiveData<String>
        get() = _warningMessage
    var preference = 0f
    var isTagsFull = false
    val memo = MutableLiveData<String>()

    fun getTags() = viewModelScope.launch {
        runCatching { reviewRepository.getTags(OunceLocalRepository.catIndex) }
            .onSuccess { _tagList.value = it.data }
            .onFailure { Log.d("TAG", it.message.toString()) }
    }

    fun addTag(tag: String) {
        runCatching {
            require(selectedTag.size < TAG_MAX_ENTRY) { "태그를 3개 이상 선택할 수 없습니다" }
        }.onSuccess {
            selectedTag.add(tag)
            if (selectedTag.size >= TAG_MAX_ENTRY) isTagsFull = true
        }.onFailure {
            _warningMessage.value = it.message
        }
    }

    fun deleteTag(tag: String) {
        selectedTag.removeIf { it == tag }
        if (selectedTag.size <= TAG_MAX_ENTRY) isTagsFull = false
    }
}