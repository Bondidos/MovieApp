package com.bondidos.movies.presentation.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieType

@Immutable
sealed class MoviesIntent : Intention {
    data class ToggleMovies(val type: MovieType) : MoviesIntent()
    data class ShowDetails(val id: Int?) : MoviesIntent()
    data object NavigateToProfile : MoviesIntent()
    data object NextAnticipatedPage : MoviesIntent()
    data object NextTrendingPage : MoviesIntent()
    data object SingOut : MoviesIntent()
    data object Refresh : MoviesIntent()
}