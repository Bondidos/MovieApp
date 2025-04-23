package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Intention

@Immutable
sealed class MoviesIntent : Intention {
    data object ShowTrending: MoviesIntent()
    data object ShowAnticipated: MoviesIntent()
    data object ShowDetails: MoviesIntent()
    data object NavigateToProfile: MoviesIntent()
}