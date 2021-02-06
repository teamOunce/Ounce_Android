package com.teamounce.ounce.di

import com.teamounce.ounce.data.remote.api.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceModule {
    @Provides
    @Singleton
    fun provideLoginService(@Named("Login") retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)
}