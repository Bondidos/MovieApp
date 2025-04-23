package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieType

@Immutable
sealed class MoviesEvent : Reducer.ViewEvent {
    data object Loading : MoviesEvent()
    data class ToggleMoviesType(val moviesType: MovieType) : MoviesEvent()
}