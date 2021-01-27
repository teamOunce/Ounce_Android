package com.teamounce.ounce.di

import com.teamounce.ounce.feed.repository.FeedRepository
import com.teamounce.ounce.feed.repository.mock.MockFeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFeedRepository(repository : MockFeedRepositoryImpl): FeedRepository
}