package com.bondidos.cache.type_converter

import androidx.room.TypeConverter
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.util.*

class TrendingMovieConverters {
    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromTrendingMovieList(value: List<TrendingMovieDto>): String {
        val type = Types.newParameterizedType(List::class.java, TrendingMovieDto::class.java)
        val adapter = moshi.adapter<List<TrendingMovieDto>>(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toTrendingMovieList(value: String): List<TrendingMovieDto>? {
        val type = Types.newParameterizedType(List::class.java, TrendingMovieDto::class.java)
        val adapter = moshi.adapter<List<TrendingMovieDto>>(type)
        return adapter.fromJson(value)
    }

    // For Date
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)

}