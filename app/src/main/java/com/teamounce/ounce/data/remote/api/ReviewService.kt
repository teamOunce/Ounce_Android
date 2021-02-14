package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.review.model.ResponseReview
import com.teamounce.ounce.review.model.ResponseReviewInfo
import com.teamounce.ounce.review.model.ResponseTags
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ReviewService {
    @GET("tagList")
    suspend fun getTags(
        @Query("catIndex") catIndex: Int
    ): ResponseTags

    @Multipart
    @POST("review")
    suspend fun registerReview(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): ResponseReview

    @Multipart
    @PUT("review")
    suspend fun modifyReview(
        @PartMap body: HashMap<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): ResponseReview

    @GET("review")
    suspend fun getReviewInfo(
        @Query("reviewIndex") reviewIndex: Int
    ): ResponseReviewInfo

    @DELETE("review")
    suspend fun deleteReview(
        @Query("reviewIndex") reviewIndex: Int
    ): ResponseReview
}