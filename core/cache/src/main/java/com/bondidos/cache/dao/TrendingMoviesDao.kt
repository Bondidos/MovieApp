package com.bondidos.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bondidos.cache.entity.TrendingMoviesCacheEntity

@Dao
interface TrendingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TrendingMoviesCacheEntity)

    @Query("SELECT * FROM trending_movies_cache WHERE page = :page LIMIT 1")
    suspend fun get(page: Int): TrendingMoviesCacheEntity?
}