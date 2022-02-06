package com.teamounce.ounce.feed.viewmodel

import androidx.lifecycle.*
import com.teamounce.ounce.data.enum.EnumFeedSort
import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.data.remote.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _foodReview = MutableLiveData<Review>()
    val foodReview: LiveData<Review>
        get() = _foodReview
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    val preferenceLiveData= _foodReview.map {
        it?.let {
            return@map it.catFoodReview.preference
        } ?: 0
    }

    fun fetchReviewData(reviewIndex: Int) {
        viewModelScope.launch {
            _foodReview.value = feedRepository.getFoodReviewList(RequestReview(reviewIndex))
        }
    }

    fun deleteReview(reviewIndex: Int) {
        viewModelScope.launch {
            runCatching { feedRepository.deleteReview(reviewIndex) }
                .onSuccess { _isSuccess.value = true }
                .onFailure { }
        }
    }
}