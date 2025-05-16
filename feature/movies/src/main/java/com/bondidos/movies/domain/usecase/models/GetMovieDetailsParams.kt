package com.bondidos.movies.domain.usecase.models

import com.bondidos.ui.composables.MovieType

data class GetMovieDetailsParams(
    val traktId: Int?,
    val type: MovieType,
    val page: Int,
)
