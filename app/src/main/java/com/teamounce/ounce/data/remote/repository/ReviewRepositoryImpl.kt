package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.ReviewService
import com.teamounce.ounce.review.model.ResponseReview
import com.teamounce.ounce.review.model.ResponseReviewInfo
import com.teamounce.ounce.review.model.ResponseTags
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun registerReview(
        body: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): ResponseReview = reviewService.registerReview(body, image)

    override suspend fun getTags(catIndex: Int): ResponseTags = reviewService.getTags(catIndex)

    override suspend fun modifyReview(
        body: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): ResponseReview = reviewService.modifyReview(body, image)

    override suspend fun deleteReview(reviewIndex: Int): ResponseReview =
        reviewService.deleteReview(reviewIndex)

    override suspend fun getReviewIndo(reviewIndex: Int): ResponseReviewInfo =
        reviewService.getReviewInfo(reviewIndex)
}