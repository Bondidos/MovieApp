package com.bondidos.movies.domain.model

data class AnticipatedMovie(
    val title: String,
    val genre: String,
    val certification: String,
    val image: String,
    val stars: Int,
    val duration: String,
)
