package com.bondidos.movies.domain.model

data class TrendingMovie(
    val title: String,
    val genre: String,
    val certification: String,
    val image: String,
    val stars: Int,
    val duration: String,
)
