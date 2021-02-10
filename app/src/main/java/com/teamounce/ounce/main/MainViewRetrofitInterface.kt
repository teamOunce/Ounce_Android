package com.teamounce.ounce.main

import com.teamounce.ounce.R
import retrofit2.Call
import retrofit2.http.*

interface MainViewRetrofitInterface {
    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjE3LCJleHAiOjE2MTI5MzYzMDh9.aHuUbnjSdgRGqkD76C5jjtPxNvvQb81-tf0zERAGczg"])
    @GET("/main")
    fun mainViewRetrofit(@Query("catIndex") catIndex: Int): Call<MainViewResponseData>


    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjE3LCJleHAiOjE2MTI5MzYzMDh9.aHuUbnjSdgRGqkD76C5jjtPxNvvQb81-tf0zERAGczg"])
    @GET("/main/getList")
    fun bottomSheetProfileRetrofit(@Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>

    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjE3LCJleHAiOjE2MTI5MzYzMDh9.aHuUbnjSdgRGqkD76C5jjtPxNvvQb81-tf0zERAGczg"])
    @DELETE("/main/profile")
    fun mainDeleteRetrofit(@Query("catIndex") catIndex: Int): Call<MainDeleteResponseData>

}