package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.movies.domain.model.Movie
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieType

@Immutable
data class MoviesState(
    val isLoading: Boolean,
    val moviesType: MovieType,
    val trendingMovies: List<Movie>,
    val trendingPage: Int,
    val anticipatedMovies: List<Movie>,
    val anticipatedPage: Int,
) : Reducer.ViewState {

    companion object {
        fun init() = MoviesState(
            isLoading = false,
            moviesType = MovieType.Trending,
            trendingMovies = emptyList(),
            trendingPage = 1,
            anticipatedMovies = emptyList(),
            anticipatedPage = 1,
        )
    }
}