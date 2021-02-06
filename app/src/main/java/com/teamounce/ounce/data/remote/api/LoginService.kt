package com.teamounce.ounce.data.remote.api

import com.teamounce.ounce.login.model.RequestGoogleLogin
import com.teamounce.ounce.login.model.RequestKakaoLogin
import com.teamounce.ounce.login.model.ResponseLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("login/google")
    suspend fun postUserByGoogleLogin(
        @Body requestGoogleLogin: RequestGoogleLogin
    ): ResponseLogin

    @POST("login/kakao")
    suspend fun postUserByKakaoLogin(
        @Body requestKakaoLogin: RequestKakaoLogin
    ): ResponseLogin

    @GET("getRefresh")
    suspend fun refreshToken(): ResponseLogin
}