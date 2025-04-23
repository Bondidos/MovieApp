package com.bondidos.movies.domain.usecase.modles

sealed class GetMoviesParams {
    data class GetTrending(val page: Int) : GetMoviesParams()
    data class GetAnticipated(val page: Int) : GetMoviesParams()
}