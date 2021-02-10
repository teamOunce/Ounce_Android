package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.feed.model.ResponseFeedReviewData

interface FeedReviewRepository {
    suspend fun getReviewFilterList(
        tag : String? = null,
    type : String? = null,
    manu : String? = null) : ResponseFeedReviewData
}