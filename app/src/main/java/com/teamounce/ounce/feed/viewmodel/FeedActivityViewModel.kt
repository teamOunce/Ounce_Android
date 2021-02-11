package com.teamounce.ounce.feed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.FeedReviewRepository
import com.teamounce.ounce.feed.model.ResponseFeedListData
import com.teamounce.ounce.feed.model.ResponseFilterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedActivityViewModel @Inject constructor(private val repo : FeedReviewRepository)
    : ViewModel(){

    private val TAG = "FeedActivity"
    val feedList = MutableLiveData<List<ResponseFeedListData>>(
        listOf(
            ResponseFeedListData(brand = "브랜드", title = "타이틀", count = 0)
        )
    )

    var tagFilterList = mutableMapOf<String,Boolean>()
    var brandFilterList = mutableMapOf<String,Boolean>()
    // BottomSheetFilter Check Parameter
    var filterSet = MutableLiveData(false)


//    var tagFilterSample2 = mutableMapOf(
//        "#테스트" to false,
//        "#테스트2" to false,
//        "#테스트3" to false,
//        "#테스트4" to false
//    )
//
//    var brandFilterSample2 = mutableMapOf(
//        "#브랜드 테스트" to false,
//        "#브랜드 테스트2" to false
//    )

    fun getBottomSheetFiltering(){
        viewModelScope.launch {
            runCatching {
                repo.getBottomSheetFilterList(OunceLocalRepository.catIndex)
            }.onSuccess {
                setFilter(it.data)
            }

        }
    }

    private suspend fun setFilter(data : ResponseFilterData.Data){
        runCatching {
            for (i in data.tag){
                tagFilterList["#${i}"] = false
            }
            for (i in data.manu){
                brandFilterList["#${i}"] = false
            }
        }.onSuccess {
            filterSet.value = true
        }.onFailure {  e ->
            Log.e(TAG,"필터 바텀시트 생성 오류")
            e.printStackTrace()
        }

    }
}