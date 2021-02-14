package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.main.BottomSheetResponseData
import com.teamounce.ounce.main.MainDeleteResponseData
import com.teamounce.ounce.main.MainViewResponseData
import com.teamounce.ounce.settings.model.ResponseExpire
import retrofit2.Call
import retrofit2.http.*

interface MainService {
    @GET("main")
    fun mainViewRetrofit(@Header("token") token: String, @Query("catIndex") catIndex: Int): Call<MainViewResponseData>

    @GET("main/getList")
    fun bottomSheetProfileRetrofit(@Header("token") token: String, @Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>

    @DELETE("main/profile")
    fun mainDeleteRetrofit(@Query("catIndex") catIndex: Int): Call<MainDeleteResponseData>

    @DELETE("deleteUser")
    fun deleteUser() : Call<ResponseExpire>
}