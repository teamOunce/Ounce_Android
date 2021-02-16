package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.review.model.ResponseReview

interface FeedRepository {
    suspend fun getFoodReviewList(reviewIndex: RequestReview): Review
    suspend fun deleteReview(catIndex: Int): ResponseReview
}