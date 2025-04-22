package com.bondidos.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrendingMoviesCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieCacheDao(): MovieCacheDao
}