package com.bondidos.movies.domain.model

data class MovieDetails(
    val title: String,
    val overview: String,
    val durationAndCertification: String,
    val genres: String,
    val image: String,
    val stars: Int,
    val rating: String,
    val id: Int,
)
