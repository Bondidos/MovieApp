package com.bondidos.network.dto.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfilesDto(
    val aspectRatio: Double?,
    val height: Int?,
    val iso6391: String?,
    val filePath: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val width: Int?,
)
