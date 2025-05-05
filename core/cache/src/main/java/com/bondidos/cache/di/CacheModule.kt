package com.bondidos.cache.di

import android.content.Context
import androidx.room.Room
import com.bondidos.cache.MovieDatabase
import com.bondidos.cache.dao.AnticipatedMoviesDao
import com.bondidos.cache.dao.CrewAndCastDao
import com.bondidos.cache.dao.CrewAndCastImagesDao
import com.bondidos.cache.dao.TrendingMoviesDao
import com.bondidos.cache.type_converter.AnticipatedMovieConverter
import com.bondidos.cache.type_converter.CrewAndCastConverter
import com.bondidos.cache.type_converter.CrewAndCastImageConverter
import com.bondidos.cache.type_converter.TrendingMovieConverter
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
        trendingMovieConverters: TrendingMovieConverter,
        anticipatedMovieConverter: AnticipatedMovieConverter,
        crewAndCastConverter: CrewAndCastConverter,
        crewAndCastImageConverter: CrewAndCastImageConverter
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-database"
        )
            .addTypeConverter(trendingMovieConverters)
            .addTypeConverter(anticipatedMovieConverter)
            .addTypeConverter(crewAndCastConverter)
            .addTypeConverter(crewAndCastImageConverter)
            .build()
    }

    @Provides
    fun provideTrendingMoviesDao(database: MovieDatabase): TrendingMoviesDao =
        database.trendingMovieDao()


    @Provides
    fun provideTrendingMovieConverters(): TrendingMovieConverter = TrendingMovieConverter()

    @Provides
    fun provideAnticipatedMoviesDao(database: MovieDatabase): AnticipatedMoviesDao =
        database.anticipatedMovieDao()

    @Provides
    fun provideAnticipatedMovieConverter(): AnticipatedMovieConverter = AnticipatedMovieConverter()

    @Provides
    fun provideCrewAndCastDao(
        database: MovieDatabase
    ): CrewAndCastDao = database.crewAndCastDao()

    @Provides
    fun provideCrewAndCastConverter(): CrewAndCastConverter = CrewAndCastConverter()

    @Provides
    fun provideCrewAndCastImageDao(
        database: MovieDatabase
    ): CrewAndCastImagesDao = database.crewAndCastImageDao()

    @Provides
    fun provideCrewAndCastImageConverter(): CrewAndCastImageConverter = CrewAndCastImageConverter()
}