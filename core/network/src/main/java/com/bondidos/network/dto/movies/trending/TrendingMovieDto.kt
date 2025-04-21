package com.bondidos.network.dto.movies.trending

import com.bondidos.network.dto.movies.common.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendingMovieDto(
    @Json(name = "movie")
    val movie: MovieDto,

    @Json(name = "watchers")
    val watchers: Int
)
