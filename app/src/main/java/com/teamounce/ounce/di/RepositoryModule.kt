package com.teamounce.ounce.di

import com.teamounce.ounce.data.remote.repository.LoginRepository
import com.teamounce.ounce.data.remote.repository.LoginRepositoryImpl
import com.teamounce.ounce.feed.repository.FeedRepository
import com.teamounce.ounce.feed.repository.mock.MockFeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFeedRepository(repository : MockFeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository
}