package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.settings.model.RequestAddCatProfile
import com.teamounce.ounce.settings.model.ResponseAddCatProfile
import com.teamounce.ounce.settings.model.ResponseCatProfiles
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SettingService {
    @POST("main/addProfile")
    suspend fun addCatProfile(
        @Body catProfile: RequestAddCatProfile
    ): ResponseAddCatProfile

    @GET("main/getList")
    suspend fun getCatList(
        @Query("catIndex") catIndex: Int
    ): ResponseCatProfiles
}