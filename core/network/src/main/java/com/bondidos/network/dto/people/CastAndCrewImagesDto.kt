package com.bondidos.network.dto.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CastAndCrewImagesDto(
    val id: Int?,
    val profiles: List<ProfilesDto>?,
)
