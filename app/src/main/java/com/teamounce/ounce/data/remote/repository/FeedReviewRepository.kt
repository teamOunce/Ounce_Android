package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.feed.model.ResponseFilterData

interface FeedReviewRepository {
    suspend fun getReviewFilterList(
        tag : String?,
    type : String?,
    manu : String?,
    catIndex: Int) : ResponseFeedReviewData

    suspend fun getBottomSheetFilterList(
        catIndex : Int
    ) : ResponseFilterData
}