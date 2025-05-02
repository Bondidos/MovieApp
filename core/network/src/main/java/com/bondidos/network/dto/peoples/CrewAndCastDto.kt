package com.bondidos.network.dto.peoples

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewAndCastDto(
    val  cast: List<CastDto>?,
    val  crew: Map<String,List<CrewMemberDto>?>?
)