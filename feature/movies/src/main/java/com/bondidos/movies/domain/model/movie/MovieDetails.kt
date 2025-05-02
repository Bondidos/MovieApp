package com.bondidos.movies.domain.model.movie

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
