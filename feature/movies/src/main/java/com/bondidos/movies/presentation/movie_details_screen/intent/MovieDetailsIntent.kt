package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieDetailsType

@Immutable
sealed class MovieDetailsIntent : Intention {
    data object GoBack : MovieDetailsIntent()
    data object PlayTrailer : MovieDetailsIntent()
    data object ShareTrailerLink : MovieDetailsIntent()
    data class MovieDetailsTypeChanged(val type: MovieDetailsType) : MovieDetailsIntent()
}