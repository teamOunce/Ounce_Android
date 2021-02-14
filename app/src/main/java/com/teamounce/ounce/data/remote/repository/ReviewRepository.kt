package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.review.model.ResponseReview
import com.teamounce.ounce.review.model.ResponseReviewInfo
import com.teamounce.ounce.review.model.ResponseTags
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReviewRepository {
    suspend fun registerReview(
        body: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): ResponseReview

    suspend fun getTags(catIndex: Int): ResponseTags

    suspend fun modifyReview(
        body: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): ResponseReview

    suspend fun deleteReview(reviewIndex: Int): ResponseReview

    suspend fun getReviewIndo(reviewIndex: Int): ResponseReviewInfo
}
