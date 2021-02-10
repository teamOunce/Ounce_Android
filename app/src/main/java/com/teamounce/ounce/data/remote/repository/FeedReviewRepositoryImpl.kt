package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.FeedService
import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import javax.inject.Inject

class FeedReviewRepositoryImpl @Inject constructor(private val feedService: FeedService)
    : FeedReviewRepository{

    override suspend fun getReviewFilterList(
        tag: String?,
        type: String?,
        manu: String?
    ): ResponseFeedReviewData = feedService.getFeedList(tag = tag, type = type, manu = manu)
}