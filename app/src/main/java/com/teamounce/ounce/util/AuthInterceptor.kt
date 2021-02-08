package com.teamounce.ounce.util

import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        OunceLocalRepository.userAccessToken.let {
            requestBuilder.addHeader("token", it)
        }

        return chain.proceed(requestBuilder.build())
    }
}