package com.teamounce.ounce.main

import retrofit2.Call
import retrofit2.http.*

interface MainViewRetrofitInterface {
    @Headers(value = ["Content-Type: application/json","token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMTgyNTQyMH0.zQbBRSelroEocFeHOK_NGuL7ipjxxnmfPnLsocgMemo"])
    @GET("/main")
    fun mainViewRetrofit(@Query("catIndex") catIndex: Int): Call<MainViewResponseData>


    @Headers(value = ["Content-Type: application/json",
        "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMTgyNTQyMH0.zQbBRSelroEocFeHOK_NGuL7ipjxxnmfPnLsocgMemo"])
    @GET("/main/getList")
    fun bottomSheetProfileRetrofit(@Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>
}