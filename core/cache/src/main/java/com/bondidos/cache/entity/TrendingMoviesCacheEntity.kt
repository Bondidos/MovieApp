package com.bondidos.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bondidos.cache.type_converter.TrendingMovieConverter
import com.bondidos.network.dto.movies.trending.TrendingMovieDto

@Entity(tableName = "trending_movies_cache")
@TypeConverters(TrendingMovieConverter::class)
data class TrendingMoviesCacheEntity(
    @PrimaryKey
    val page: Int,
    val movies: List<TrendingMovieDto>,
    val createdAt: Long = System.currentTimeMillis(),
)
