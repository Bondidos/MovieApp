package com.bondidos.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bondidos.cache.type_converter.AnticipatedMovieConverter
import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto

@Entity(tableName = "anticipated_movies_cache")
@TypeConverters(AnticipatedMovieConverter::class)
data class AnticipatedMoviesCacheEntity(
    @PrimaryKey
    val page: Int,
    val movies: List<AnticipatedMovieDto>,
    val createdAt: Long = System.currentTimeMillis(),
)