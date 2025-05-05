package com.bondidos.movies.presentation.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class MoviesEffect: Reducer.ViewEffect {
    data class ShowErrorMessage(val message: String): MoviesEffect()
}