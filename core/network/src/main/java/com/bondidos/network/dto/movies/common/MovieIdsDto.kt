package com.bondidos.network.dto.movies.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieIdsDto(
    @Json(name = "trakt") val trakt: Int?,
    @Json(name = "slug") val slug: String?,
    @Json(name = "imdb") val imdb: String?,
    @Json(name = "tmdb") val tmdb: Int?
)