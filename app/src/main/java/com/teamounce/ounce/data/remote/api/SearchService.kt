package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.review.model.ResponseSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("products")
    suspend fun search(
        @Query("word") word: String,
        @Query("catIndex") catIndex: Int
    ): ResponseSearch
}