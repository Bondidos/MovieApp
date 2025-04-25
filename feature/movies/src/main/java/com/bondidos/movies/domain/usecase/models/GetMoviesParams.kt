package com.bondidos.movies.domain.usecase.models

sealed class GetMoviesParams {
    data class GetTrending(val page: Int) : GetMoviesParams()
    data class GetAnticipated(val page: Int) : GetMoviesParams()
}