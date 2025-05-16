package com.bondidos.cache.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bondidos.network.adapters.DateAdapter
import com.bondidos.network.dto.people.ProfilesDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ProvidedTypeConverter
class CrewAndCastImageConverter {
    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun fromCrewAndCastDto(value: List<ProfilesDto>): String {
        val jsonAdapter: JsonAdapter<List<ProfilesDto>> = moshi.adapter<List<ProfilesDto>>()
        return jsonAdapter.toJson(value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun toCrewAndCastDto(value: String): List<ProfilesDto>? {
        val jsonAdapter: JsonAdapter<List<ProfilesDto>> = moshi.adapter<List<ProfilesDto>>()
        return jsonAdapter.fromJson(value)
    }
}