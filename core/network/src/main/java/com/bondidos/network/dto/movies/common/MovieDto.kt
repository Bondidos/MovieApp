package com.bondidos.network.dto.movies.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "title") val title: String,
    @Json(name = "year") val year: Int,
    @Json(name = "ids") val ids: MovieIdsDto
)
