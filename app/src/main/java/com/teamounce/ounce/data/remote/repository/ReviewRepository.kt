package com.teamounce.ounce.data.remote.repository

import com.teamounce.ounce.review.model.ResponseRegisterReview
import com.teamounce.ounce.review.model.ResponseTags
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ReviewRepository {
    suspend fun registerReview(
        body: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): ResponseRegisterReview

    suspend fun getTags(catIndex: Int): ResponseTags
}
