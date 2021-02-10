package com.teamounce.ounce.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _isListSearch = MutableLiveData(true)
    val isListSearch: LiveData<Boolean>
        get() = _isListSearch
    val searchQuery = MutableLiveData<String>()
}