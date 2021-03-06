package com.teamounce.ounce.data.remote.repository.mock

import com.teamounce.ounce.feed.model.RequestReview
import com.teamounce.ounce.feed.model.Review
import com.teamounce.ounce.data.remote.repository.FeedRepository
import com.teamounce.ounce.review.model.ResponseReview
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockFeedRepositoryImpl @Inject constructor() : FeedRepository {
    override suspend fun getFoodReviewList(reviewIndex: RequestReview): Review {
        return Review(
            responseMessage = "리뷰리스트 가져오기 성공",
            catFoodReview = Review.CatFoodReview(
                manufacturer = "내추럴발란스",
                memo = "미안해 이건 너무 맛있다",
                myImg = "https://image.auction.co.kr/itemimage/10/1f/c3/101fc35e36.jpg",
                preference = 5,
                productImg = "https://image.auction.co.kr/itemimage/10/1f/c3/101fc35e36.jpg",
                productName = "수제스튜 참치새우연어",
                createdDate = "2021-01-23 16:03:18",
                updatedDate = "2021-01-23 16:03:18",
                tag1 = "JMT",
                tag2 = "",
                tag3 = ""
            )
        )
    }

    override suspend fun deleteReview(catIndex: Int): ResponseReview {
        return ResponseReview(responseMessage = "", data = 1)
    }
}