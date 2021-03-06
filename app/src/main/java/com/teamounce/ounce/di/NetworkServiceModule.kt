package com.teamounce.ounce.di

import com.teamounce.ounce.data.remote.api.FeedService
import com.teamounce.ounce.data.remote.api.LoginService
import com.teamounce.ounce.data.remote.api.RegisterService
import com.teamounce.ounce.data.remote.api.ReviewService
import com.teamounce.ounce.data.remote.api.SearchService
import com.teamounce.ounce.data.remote.api.*
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
    fun provideLoginService(@Named("Default") retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideRegisterService(@Named("Default") retrofit: Retrofit): RegisterService =
        retrofit.create(RegisterService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(@Named("Default") retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideReviewService(@Named("Default") retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)

    @Provides
    @Singleton
    fun provideFeedService(@Named("Default") retrofit: Retrofit) : FeedService =
        retrofit.create(FeedService::class.java)

    @Provides
    @Singleton
    fun provideTagService(@Named("Default") retrofit: Retrofit): TagService =
        retrofit.create(TagService::class.java)

    @Provides
    @Singleton
    fun provideAddCatServoce(@Named("Default") retrofit: Retrofit): SettingService =
        retrofit.create(SettingService::class.java)
}