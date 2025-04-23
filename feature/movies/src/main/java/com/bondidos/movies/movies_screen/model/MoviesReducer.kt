package com.bondidos.movies.movies_screen.model

import com.bondidos.movies.movies_screen.intent.MoviesEffect
import com.bondidos.movies.movies_screen.intent.MoviesEvent
import com.bondidos.movies.movies_screen.intent.MoviesState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class MoviesReducer @Inject constructor() : Reducer<MoviesState, MoviesEvent, MoviesEffect> {
    override fun reduce(
        previousState: MoviesState,
        event: MoviesEvent
    ): Pair<MoviesState, MoviesEffect?> {
        return when (event) {
            is MoviesEvent.Loading -> previousState.copy(isLoading = true) to null
            is MoviesEvent.ToggleMoviesType -> {
                // todo Temp solution. ToggleMoviesType should contain list of movies?
                return previousState.copy(moviesType = event.moviesType, isLoading = false) to null
            }
        }
    }
}