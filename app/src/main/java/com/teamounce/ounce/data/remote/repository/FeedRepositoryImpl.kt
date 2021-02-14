package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.FeedService
import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.review.model.ResponseReview
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val service: FeedService
) : FeedRepository {
    override suspend fun getFoodReviewList(reviewIndex: RequestReview): Review =
        service.getReview(reviewIndex = reviewIndex.reviewIndex)

    override suspend fun deleteReview(catIndex: Int) =
        service.deleteReview(catIndex)
}