package com.teamounce.ounce.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamounce.ounce.feed.model.ResponseFeedListData

class FeedActivityViewModel : ViewModel() {

    val feedList = MutableLiveData<List<ResponseFeedListData>>(
        listOf(
            ResponseFeedListData(brand = "브랜드", title = "타이틀", count = 0)
        )
    )

    val tagFilterSample = listOf("#테스트","#테스트")
    val brandFilterSample = listOf("#브랜드 테스트","# 브랜드 테스트")

}