package com.teamounce.ounce.main

import com.teamounce.ounce.R
import retrofit2.Call
import retrofit2.http.*

interface MainViewRetrofitInterface {
    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjIzNzY1NH0.6jHdQeHw7bEsQrcMfxZPbEq7oR3M-q06U-WH3MMjhSQ"])
    @GET("/main")
    fun mainViewRetrofit(@Query("catIndex") catIndex: Int): Call<MainViewResponseData>


    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjIzNzY1NH0.6jHdQeHw7bEsQrcMfxZPbEq7oR3M-q06U-WH3MMjhSQ"])
    @GET("/main/getList")
    fun bottomSheetProfileRetrofit(@Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>

    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjIzNzY1NH0.6jHdQeHw7bEsQrcMfxZPbEq7oR3M-q06U-WH3MMjhSQ"])
    @DELETE("/main/profile")
    fun mainDeleteRetrofit(@Query("catIndex") catIndex: Int): Call<MainDeleteResponseData>

}