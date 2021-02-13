package com.teamounce.ounce.di

import com.teamounce.ounce.data.remote.repository.*
import com.teamounce.ounce.data.remote.repository.mock.MockFeedRepositoryImpl
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
    abstract fun bindFeedRepository(repository: MockFeedRepositoryImpl): FeedRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(repository: RegisterRepositoryImpl): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(repository: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(repository: ReviewRepositoryImpl): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindFeedReviewRepository(repository: FeedReviewRepositoryImpl) : FeedReviewRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(repository: TagRepositoryImpl): TagRepository
}