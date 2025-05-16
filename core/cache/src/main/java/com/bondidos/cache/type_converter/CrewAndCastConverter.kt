package com.bondidos.cache.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.bondidos.network.adapters.DateAdapter
import com.bondidos.network.dto.people.CrewAndCastDto
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ProvidedTypeConverter
class CrewAndCastConverter {
    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun fromCrewAndCastDto(value: CrewAndCastDto): String {
        val jsonAdapter: JsonAdapter<CrewAndCastDto> = moshi.adapter<CrewAndCastDto>()
        return jsonAdapter.toJson(value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @TypeConverter
    fun toCrewAndCastDto(value: String): CrewAndCastDto? {
        val jsonAdapter: JsonAdapter<CrewAndCastDto> = moshi.adapter<CrewAndCastDto>()
        return jsonAdapter.fromJson(value)
    }
}