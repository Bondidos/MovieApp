package com.bondidos.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bondidos.cache.type_converter.TrendingMovieConverters
import com.bondidos.network.dto.movies.trending.TrendingMovieDto

@Entity(tableName = "trending_movies_cache")
@TypeConverters(TrendingMovieConverters::class)
data class TrendingMoviesCacheEntity(
    @PrimaryKey
    val page: Int,
    val movies: List<TrendingMovieDto>,
    val createdAt: Long = System.currentTimeMillis(),
)
