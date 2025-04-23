package com.bondidos.cache.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bondidos.network.adapters.DateAdapter
import com.bondidos.network.dto.movies.trending.TrendingMovieDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ProvidedTypeConverter
class TrendingMovieConverter {
    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun fromTrendingMovieList(value: List<TrendingMovieDto>): String {
        val jsonAdapter: JsonAdapter<List<TrendingMovieDto>> = moshi.adapter<List<TrendingMovieDto>>()
        return jsonAdapter.toJson(value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun toTrendingMovieList(value: String): List<TrendingMovieDto>? {
        val jsonAdapter: JsonAdapter<List<TrendingMovieDto>> = moshi.adapter<List<TrendingMovieDto>>()
        return jsonAdapter.fromJson(value)
    }
}