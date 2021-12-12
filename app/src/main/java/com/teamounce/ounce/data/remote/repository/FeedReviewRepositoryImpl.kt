package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.FeedService
import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.feed.model.ResponseFilterData
import javax.inject.Inject

class FeedReviewRepositoryImpl @Inject constructor(private val feedService: FeedService) :
    FeedReviewRepository {

    override suspend fun getReviewFilterList(
        tag: String?,
        type: String?,
        manu: String?,
        catIndex: Int,
        sort: String?
    ): ResponseFeedReviewData =
        feedService.getFeedList(tag = tag, type = type, manu = manu, catIndex = catIndex, sort = sort)

    override suspend fun getBottomSheetFilterList(catIndex: Int)
            : ResponseFilterData = feedService.getFiltering(catIndex)
}