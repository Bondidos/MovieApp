package com.bondidos.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bondidos.cache.type_converter.CrewAndCastConverter
import com.bondidos.network.dto.people.CrewAndCastDto

@Entity(tableName = "crew_and_cast_cache")
@TypeConverters(CrewAndCastConverter::class)
data class CrewAndCastEntity(
    @PrimaryKey
    val id: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val crewAndCast: CrewAndCastDto
)
