package com.bondidos.network.dto.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponseDto(
    @Json(name = "movie")
    val movie: MovieDto,

    @Json(name = "watchers")
    val watchers: Int
)

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "title") val title: String,
    @Json(name = "year") val year: Int,
    @Json(name = "ids") val ids: MovieIdsDto
)

@JsonClass(generateAdapter = true)
data class MovieIdsDto(
    @Json(name = "trakt") val trakt: Int,
    @Json(name = "slug") val slug: String,
    @Json(name = "imdb") val imdb: String,
    @Json(name = "tmdb") val tmdb: Int
)