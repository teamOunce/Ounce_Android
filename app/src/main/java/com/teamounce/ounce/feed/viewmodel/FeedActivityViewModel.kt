package com.teamounce.ounce.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamounce.ounce.feed.model.ResponseFeedListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FeedActivityViewModel : ViewModel() {

    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    val feedList = MutableLiveData<List<ResponseFeedListData>>(
        listOf(
            ResponseFeedListData(brand = "브랜드", title = "타이틀", count = 0)
        )
    )
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}