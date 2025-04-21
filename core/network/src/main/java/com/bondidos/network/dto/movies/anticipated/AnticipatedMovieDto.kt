package com.bondidos.network.dto.movies.anticipated

import com.bondidos.network.dto.movies.common.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnticipatedMovieDto(
    @Json(name = "list_count") val listCount: Int,
    @Json(name = "movie") val movie: MovieDto
)
