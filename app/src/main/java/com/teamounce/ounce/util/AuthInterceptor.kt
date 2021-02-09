package com.teamounce.ounce.util

import com.teamounce.ounce.data.local.singleton.OunceLocalRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (OunceLocalRepository.userAccessToken != "" && !filterHeaderByRequest(chain.request())) {
            OunceLocalRepository.userAccessToken.let {
                requestBuilder.addHeader("token", it)
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private fun filterHeaderByRequest(request: Request): Boolean {
        return when {
            request.url().encodedPath() == "login/google" -> true
            request.url().encodedPath() == "login/kakao" -> true
            request.url().encodedPath() == "getRefresh" -> true
            else -> false
        }
    }
}