package com.bondidos.cache.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bondidos.network.adapters.DateAdapter
import com.bondidos.network.dto.movies.anticipated.AnticipatedMovieDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ProvidedTypeConverter
class AnticipatedMovieConverter {
    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun fromAnticipatedMovieList(value: List<AnticipatedMovieDto>): String {
        val jsonAdapter: JsonAdapter<List<AnticipatedMovieDto>> =
            moshi.adapter<List<AnticipatedMovieDto>>()
        return jsonAdapter.toJson(value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun toAnticipatedMovieList(value: String): List<AnticipatedMovieDto>? {
        val jsonAdapter: JsonAdapter<List<AnticipatedMovieDto>> =
            moshi.adapter<List<AnticipatedMovieDto>>()
        return jsonAdapter.fromJson(value)
    }
}