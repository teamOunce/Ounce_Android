package com.teamounce.ounce.di

import com.teamounce.ounce.data.remote.api.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkServiceModule {
    @Provides
    @Singleton
    fun provideLoginService(@Named("Login") retrofit: Retrofit) = retrofit.create(LoginService::class.java)
}