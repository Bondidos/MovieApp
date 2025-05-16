package com.bondidos.movies.domain.model.movie

data class Movie(
    val id: Int?,
    val title: String,
    val genre: String,
    val certification: String,
    val image: String,
    val stars: Int,
    val duration: String,
)
