package com.teamounce.ounce.feed.repository

import com.teamounce.ounce.feed.model.Feed
import com.teamounce.ounce.feed.model.RequestFeed
import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review

interface FeedRepository {
    suspend fun getFoodReviewList(reviewIndex: RequestReview): Review
}