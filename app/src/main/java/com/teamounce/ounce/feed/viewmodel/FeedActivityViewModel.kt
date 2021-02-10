package com.teamounce.ounce.feed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.teamounce.ounce.data.remote.repository.FeedReviewRepository
import com.teamounce.ounce.feed.model.ResponseFeedListData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedActivityViewModel @Inject constructor(private val repo : FeedReviewRepository)
    : ViewModel(){

    val feedList = MutableLiveData<List<ResponseFeedListData>>(
        listOf(
            ResponseFeedListData(brand = "브랜드", title = "타이틀", count = 0)
        )
    )

    val tagFilterSample = listOf("#테스트","#테스트")
    val brandFilterSample = listOf("#브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트","# 브랜드 테스트")

    var tagFilterSample2 = mutableMapOf(
        "#테스트" to false,
        "#테스트2" to false,
        "#테스트3" to false,
        "#테스트4" to false
    )

    var brandFilterSample2 = mutableMapOf(
        "#브랜드 테스트" to false,
        "#브랜드 테스트2" to false
    )
}