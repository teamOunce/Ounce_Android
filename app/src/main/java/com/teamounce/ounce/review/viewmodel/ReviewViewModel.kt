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
    private val _tagList = MutableLiveData<List<ResponseTags.Data>>()
    val tagList: LiveData<List<ResponseTags.Data>>
        get() = _tagList
    var preference = 0f
    val memo = MutableLiveData<String>()

    fun getTags() = viewModelScope.launch {
        runCatching { reviewRepository.getTags(OunceLocalRepository.catIndex) }
            .onSuccess { _tagList.value = it.data }
            .onFailure { Log.d("TAG", it.message.toString()) }
    }
}