package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.data.remote.api.ReviewService
import com.teamounce.ounce.review.model.ResponseRegisterReview
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
    ): ResponseRegisterReview = reviewService.registerReview(body, image)

    override suspend fun getTags(catIndex: Int): ResponseTags = reviewService.getTags(catIndex)
}