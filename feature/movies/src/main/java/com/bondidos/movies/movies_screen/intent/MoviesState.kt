package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieType

@Immutable
data class MoviesState(
    val isLoading: Boolean,
    val moviesType: MovieType
) : Reducer.ViewState {

    companion object {
        fun init() = MoviesState(
            isLoading = false,
            moviesType = MovieType.Trending,
        )
    }
}