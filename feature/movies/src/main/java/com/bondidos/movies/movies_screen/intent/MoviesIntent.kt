package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieType

@Immutable
sealed class MoviesIntent : Intention {
    data class ToggleMovies(val type: MovieType): MoviesIntent()
    data object ShowDetails: MoviesIntent()
    data object NavigateToProfile: MoviesIntent()
    data object NextAnticipatedPage: MoviesIntent()
    data object NextTrendingPage: MoviesIntent()
}