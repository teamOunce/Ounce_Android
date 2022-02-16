package com.teamounce.ounce.review.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.SearchRepository
import com.teamounce.ounce.review.model.ResponseSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    val searchQuery = MutableLiveData<String>()
    private val _resultList = MutableLiveData<List<ResponseSearch.Data>>()
    val resultList: LiveData<List<ResponseSearch.Data>>
        get() = _resultList

    fun search() = viewModelScope.launch {
        runCatching {
            searchRepository.search(
                query = searchQuery.value ?: "",
                catIndex = OunceLocalRepository.catIndex
            )
        }.onSuccess {
            _resultList.value = it.resultList
        }.onFailure {
            Log.d("TAG", it.message.toString())
        }
    }
}