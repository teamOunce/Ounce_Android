package com.teamounce.ounce.data.remote.api


import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {

    // 검색하기 api
    @GET("reviewList")
    suspend fun getFeedList(
        @Query("tag") tag : String? = null,
        @Query("type") type : String? = null,
        @Query("manu") manu : String? = null
    ) : ResponseFeedReviewData
}