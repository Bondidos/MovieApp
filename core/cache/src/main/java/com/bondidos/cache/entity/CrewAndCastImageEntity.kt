package com.bondidos.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bondidos.cache.type_converter.CrewAndCastImageConverter
import com.bondidos.network.dto.people.ProfilesDto

@Entity(tableName = "crew_and_cast_image_cache")
@TypeConverters(CrewAndCastImageConverter::class)
data class CrewAndCastImageEntity (
    @PrimaryKey
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val profiles: List<ProfilesDto>,
)