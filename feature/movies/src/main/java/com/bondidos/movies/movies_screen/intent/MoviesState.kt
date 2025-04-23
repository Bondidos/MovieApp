package com.bondidos.movies.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class MoviesState(
    val isLoading: Boolean,
) : Reducer.ViewState {

    companion object {
        fun init() = MoviesState(
            isLoading = false,
        )
    }
}