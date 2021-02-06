package com.teamounce.ounce.data.remote.singleton

import com.teamounce.ounce.data.remote.api.LoginService
import com.teamounce.ounce.util.AuthRefreshInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObjects {
    private const val BASE_URL = "http://15.165.252.145:8080"
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun getLoginServiceClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor())
        .addInterceptor(AuthRefreshInterceptor())
        .build()

    private val baseRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private fun getLoginRetrofitObject() = baseRetrofit.client(getLoginServiceClient()).build()

    private var loginInstance: LoginService? = null
    fun getLoginService(): LoginService = loginInstance ?: synchronized(this) {
        loginInstance ?: getLoginRetrofitObject().create(LoginService::class.java).apply {
            loginInstance = this
        }
    }
}