package com.bondidos.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TrendingMoviesCacheEntity)

    @Query("SELECT * FROM trending_movies_cache WHERE page = :page LIMIT 1")
    suspend fun get(page: Int): TrendingMoviesCacheEntity?
}