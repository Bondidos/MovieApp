package com.bondidos.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bondidos.cache.dao.AnticipatedMoviesDao
import com.bondidos.cache.dao.CrewAndCastDao
import com.bondidos.cache.dao.TrendingMoviesDao
import com.bondidos.cache.entity.AnticipatedMoviesCacheEntity
import com.bondidos.cache.entity.CrewAndCastEntity
import com.bondidos.cache.entity.TrendingMoviesCacheEntity

@Database(
    entities = [
        TrendingMoviesCacheEntity::class,
        AnticipatedMoviesCacheEntity::class,
        CrewAndCastEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun trendingMovieDao(): TrendingMoviesDao

    abstract fun anticipatedMovieDao(): AnticipatedMoviesDao

    abstract fun crewAndCastDao(): CrewAndCastDao
}