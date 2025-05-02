package com.bondidos.network.dto.peoples

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastDto(
    val character: String?,
    val characters: List<String>?,
    val person: PersonDto?,
)