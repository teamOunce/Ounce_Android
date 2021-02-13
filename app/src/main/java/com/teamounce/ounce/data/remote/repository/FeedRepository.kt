package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review

interface FeedRepository {
    suspend fun getFoodReviewList(reviewIndex: RequestReview): Review
}