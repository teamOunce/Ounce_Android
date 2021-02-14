package com.teamounce.ounce.feed.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import com.teamounce.ounce.data.remote.repository.FeedReviewRepository
import com.teamounce.ounce.feed.model.ResponseFeedListData
import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.feed.model.ResponseFilterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedActivityViewModel @Inject constructor(private val repo: FeedReviewRepository) :
    ViewModel() {

    private val TAG = "FeedActivity"

    // 기록 리스트로 보여줄 리스트들
    var responseFeedList = MutableLiveData<List<ResponseFeedReviewData.Data>>(listOf())


    var tagFilterList = mutableMapOf<String, Boolean>()
    var brandFilterList = mutableMapOf<String, Boolean>()

    // 건식 습식 체크 파라미터
    var dryCheck = false
    var wetCheck = false

    // BottomSheetFilter Check Parameter
    var filterSet = MutableLiveData(false)

    fun getBottomSheetFiltering() {
        viewModelScope.launch {
            runCatching {
                repo.getBottomSheetFilterList(OunceLocalRepository.catIndex)
            }.onSuccess {
                setFilter(it.data)
            }

        }
    }

    private suspend fun setFilter(data: ResponseFilterData.Data) {
        runCatching {
            for (i in data.tag) {
                tagFilterList["#${i}"] = false
            }
            for (i in data.manu) {
                brandFilterList["#${i}"] = false
            }
        }.onSuccess {
            filterSet.value = true
        }.onFailure { e ->
            Log.e(TAG, "필터 바텀시트 생성 오류")
            e.printStackTrace()
        }

    }

    // 필터 바텀시트에서 확인 눌렀을 경우 리스트 통신을 위한 준비
    suspend fun applicationFilter() {
        val tagList = mutableListOf<String>()
        val manuList = mutableListOf<String>()
        var typeList = mutableListOf<String>()

        var tagString: String? = null
        var manuString: String? = null
        var typeString: String? = null

        runCatching {
            for (i in tagFilterList) {
                if (i.value) {
                    val tag = i.key.replace("#", "")
                    tagList.add(tag)
                }
            }

            for (i in brandFilterList) {
                if (i.value) {
                    val manu = i.key.replace("#", "")
                    manuList.add(manu)
                }
            }

            if (tagList.isNotEmpty()) {
                tagString = tagList.joinToString(",")
            }

            if (manuList.isNotEmpty()) {
                manuString = manuList.joinToString(",")
            }
        }.onSuccess {
            callFeedList(tagString, manuString, typeString)
        }.onFailure { }

    }

    suspend fun callFeedList(tag: String? = null, manu: String? = null, type: String? = null) {
        viewModelScope.launch {
            runCatching {
                repo.getReviewFilterList(
                    tag = tag,
                    type = type,
                    manu = manu,
                    catIndex = OunceLocalRepository.catIndex
                )
            }.onSuccess {
                if (it.data.isNullOrEmpty()){
                    responseFeedList.postValue(listOf())
                }
                else{
                    responseFeedList.postValue(it.data)
                }
                Log.e("FeedActivity", "리스트 불러오기 성공")
            }.onFailure {
                Log.e("CallFeedList()", "리스트 불러오기 실패")
                println(it.printStackTrace())
            }
        }
    }
}