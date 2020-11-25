package com.teamounce.ounce.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    private val _isListSearch = MutableLiveData(true)
    val isListSearch: LiveData<Boolean>
        get() = _isListSearch
    private val _isNetworkAccess = MutableLiveData(true)
    val isNetworkAccess: LiveData<Boolean>
        get() = _isNetworkAccess

    fun changeSearchTypeToGrid() {
        _isListSearch.value = false
    }

    fun changeSearchTypeToList() {
        _isListSearch.value = true
    }

    fun isNetworkConnected() {
        _isNetworkAccess.postValue(true)
    }

    fun isNetworkDisconnected() {
        _isNetworkAccess.postValue(false)
    }
}