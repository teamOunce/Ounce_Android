package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.feed.model.RequestAddTag
import com.teamounce.ounce.feed.model.ResponseModifyTagInfo
import com.teamounce.ounce.review.model.ResponseTags
import retrofit2.http.*

interface TagService {
    @GET("tagList")
    suspend fun getTags(
        @Query("catIndex") catIndex: Int
    ): ResponseTags

    @DELETE("deleteTag")
    suspend fun deleteTag(
        @Query("tagIndex") tagIndex: Int
    ): ResponseModifyTagInfo

    @POST("addTag")
    suspend fun addTag(
        @Query("catIndex") catIndex: Int,
        @Body body: RequestAddTag
    ): ResponseModifyTagInfo
}