package com.bondidos.movies.presentation.movie_details_screen.model

import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEvent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class MovieDetailsReducer @Inject constructor() :
    Reducer<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect> {
    override fun reduce(
        previousState: MovieDetailsState,
        event: MovieDetailsEvent
    ): Pair<MovieDetailsState, MovieDetailsEffect?> {
        return when (event) {
            is MovieDetailsEvent.Loading -> previousState.copy(isLoading = true) to null
        }
    }
}