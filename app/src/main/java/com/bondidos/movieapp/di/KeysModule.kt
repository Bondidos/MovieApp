package com.bondidos.movieapp.di

import com.bondidos.movieapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object KeysModule {
    @Provides
    @Named("TRAKT_API")
    fun provideTraktApiKey() = BuildConfig.TRAKT_API

    @Provides
    @Named("TMDB_API")
    fun provideTmdbApiKey() = BuildConfig.TMDB_API
}