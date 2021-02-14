package com.teamounce.ounce.data.remote.api


import com.teamounce.ounce.feed.model.ResponseFeedReviewData
import com.teamounce.ounce.feed.model.ResponseFilterData
import com.teamounce.ounce.feed.model.Review
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {

    // 검색하기 api
    @GET("reviewList")
    suspend fun getFeedList(
        @Query("tag") tag : String?,
        @Query("type") type : String?,
        @Query("manu") manu : String?,
        @Query("catIndex") catIndex : Int
    ) : ResponseFeedReviewData

    // 필터링 목록 api
    @GET("myReviewInfo")
    suspend fun getFiltering(
        @Query("catIndex") catIndex : Int
    ) : ResponseFilterData

    @GET("review")
    suspend fun getReview(
        @Query("reviewIndex") reviewIndex: Int
    ): Review
}