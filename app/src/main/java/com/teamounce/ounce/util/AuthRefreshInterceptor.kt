package com.teamounce.ounce.util

import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthRefreshInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        OunceLocalRepository.userRefreshToken.let {
            requestBuilder.addHeader("refresh", it)
        }

        return chain.proceed(requestBuilder.build())
    }
}