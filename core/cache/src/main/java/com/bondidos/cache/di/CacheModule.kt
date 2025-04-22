package com.bondidos.cache.di

import android.content.Context
import androidx.room.Room
import com.bondidos.cache.MovieDatabase
import com.bondidos.cache.TrendingMoviesDao
import com.bondidos.cache.type_converter.TrendingMovieConverters
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        trendingMovieConverters: TrendingMovieConverters
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-database"
        )
            .addTypeConverter(trendingMovieConverters)
            .build()
    }

    @Provides
    fun provideTrendingMoviesDao(database: MovieDatabase): TrendingMoviesDao =
        database.trendingMovieDao()


    @Provides
    fun provideTrendingMovieConverters(): TrendingMovieConverters = TrendingMovieConverters()
}