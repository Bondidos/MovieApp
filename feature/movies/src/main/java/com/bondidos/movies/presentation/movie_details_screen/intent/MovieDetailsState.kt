package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class MovieDetailsState(
    val isLoading: Boolean,
) : Reducer.ViewState {

    companion object {
        fun init() = MovieDetailsState(
            isLoading = false,
        )
    }
}