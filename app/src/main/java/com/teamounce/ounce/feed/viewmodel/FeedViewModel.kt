package com.teamounce.ounce.feed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.feed.repository.FeedRepository
import kotlinx.coroutines.launch

class FeedViewModel(private val feedRepository: FeedRepository) : ViewModel() {
    private val _foodReview = MutableLiveData<Review>()
    val foodReview: LiveData<Review>
        get() = _foodReview

    fun fetchReviewData() {
        viewModelScope.launch {
            _foodReview.value = feedRepository.getFoodReviewList(RequestReview(1))
        }
    }
}