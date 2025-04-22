package com.bondidos.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TrendingMoviesCacheEntity)

    @Query("SELECT * FROM trending_movies_cache WHERE id = :id LIMIT 1")
    suspend fun get(id: String = "trending_list"): TrendingMoviesCacheEntity?

    @Query("DELETE FROM trending_movies_cache WHERE id = :id")
    suspend fun clear(id: String = "trending_list")

    @Query("SELECT COUNT(*) FROM trending_movies_cache WHERE id = :id")
    suspend fun hasData(id: String = "trending_list"): Int
}