package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class MovieDetailsEffect : Reducer.ViewEffect {
    data class ShowErrorMessage(val message: String) : MovieDetailsEffect()
    data class PlayTrailer(val url: String) : MovieDetailsEffect()
    data class Share(
        val title: String,
        val url: String,
    ) : MovieDetailsEffect()
}