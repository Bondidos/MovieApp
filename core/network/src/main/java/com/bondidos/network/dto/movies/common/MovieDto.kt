package com.bondidos.network.dto.movies.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "title") val title: String,
    @Json(name = "year") val year: Int,
    @Json(name = "ids") val ids: MovieIdsDto,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "released") val released: String,
    @Json(name = "runtime") val runtime: Int,
    @Json(name = "country") val country: String,
    @Json(name = "trailer") val trailer: String?,
    @Json(name = "homepage") val homepage: String?,
    @Json(name = "status") val status: String,
    @Json(name = "rating") val rating: Double,
    @Json(name = "votes") val votes: Int,
    @Json(name = "comment_count") val commentCount: Int,
    @Json(name = "updated_at") val updatedAt: Date,
    @Json(name = "language") val language: String,
    @Json(name = "languages") val languages: List<String>,
    @Json(name = "available_translations") val availableTranslations: List<String>,
    @Json(name = "genres") val genres: List<String>,
    @Json(name = "certification") val certification: String?,
    @Json(name = "original_title") val originalTitle: String
)
