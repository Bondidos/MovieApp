package com.bondidos.network.dto.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleIdsDto(
    val trakt: Int?,
    val slug: String?,
    val imdb: String?,
    val tmdb: Int?,
    val tvrage: Int?,
)
