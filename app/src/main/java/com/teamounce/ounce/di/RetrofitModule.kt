package com.teamounce.ounce.di

import com.teamounce.ounce.util.AuthRefreshInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {
    private val BASE_URL = "http://15.165.252.145:8080"
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val loginClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor())
        .addInterceptor(AuthRefreshInterceptor())
        .build()

    @Provides
    @Singleton
    @Named("Login")
    fun provideLoginRetrofitObject(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(loginClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}