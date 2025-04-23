package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class MoviesEvent: Reducer.ViewEvent {
    data object Loading : MoviesEvent()

}