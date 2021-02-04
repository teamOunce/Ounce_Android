package com.teamounce.ounce.main

import com.teamounce.ounce.R
import retrofit2.Call
import retrofit2.http.*

interface MainViewRetrofitInterface {
    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjUyMTU2M30.MdVPQ7vOXiBBT9NMbyfrfHwQPNu3adb-42HdNSy7Oxc"])
    @GET("/main")
    fun mainViewRetrofit(@Query("catIndex") catIndex: Int): Call<MainViewResponseData>


    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjUyMTU2M30.MdVPQ7vOXiBBT9NMbyfrfHwQPNu3adb-42HdNSy7Oxc"])
    @GET("/main/getList")
    fun bottomSheetProfileRetrofit(@Query("catIndex") catIndex: Int): Call<BottomSheetResponseData>

    @Headers(value = ["Content-Type: application/json", "token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPdW5jZSIsInVzZXJJZHgiOjksImV4cCI6MTYxMjUyMTU2M30.MdVPQ7vOXiBBT9NMbyfrfHwQPNu3adb-42HdNSy7Oxc"])
    @DELETE("/main/profile")
    fun mainDeleteRetrofit(@Query("catIndex") catIndex: Int): Call<MainDeleteResponseData>

}