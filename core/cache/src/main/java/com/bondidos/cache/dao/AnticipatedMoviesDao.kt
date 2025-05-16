package com.bondidos.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bondidos.cache.entity.AnticipatedMoviesCacheEntity

@Dao
interface AnticipatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AnticipatedMoviesCacheEntity)

    @Query("SELECT * FROM anticipated_movies_cache WHERE page = :page LIMIT 1")
    suspend fun get(page: Int): AnticipatedMoviesCacheEntity?
}