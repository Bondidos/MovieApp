package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class MovieDetailsEvent : Reducer.ViewEvent {
    data object Loading : MovieDetailsEvent()
}