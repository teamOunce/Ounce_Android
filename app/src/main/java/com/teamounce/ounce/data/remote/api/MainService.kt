package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.main.BottomSheetResponseData
import com.teamounce.ounce.main.MainDeleteResponseData
import com.teamounce.ounce.main.MainViewResponseData
import com.teamounce.ounce.settings.model.ResponseExpire
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("main")
    fun mainViewRetrofit(@Query("catIndex") catIndex: Int): Call<MainViewResponseData>

    @GET("main/getList")
    fun bottomSheetProfileRetrofit(@Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>

    @DELETE("main/profile")
    fun mainDeleteRetrofit(@Query("catIndex") catIndex: Int): Call<MainDeleteResponseData>

    @DELETE("deleteUser")
    fun deleteUser() : Call<ResponseExpire>
}