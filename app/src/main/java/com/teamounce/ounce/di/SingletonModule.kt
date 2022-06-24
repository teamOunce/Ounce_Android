package com.teamounce.ounce.di

import android.content.Context
import com.teamounce.ounce.util.CatInfoStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {
    @Singleton
    @Provides
    fun provideCatInfoStore(@ApplicationContext context: Context): CatInfoStore =
        CatInfoStore(context)
}
