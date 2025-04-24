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
            is MoviesEvent.ToggleMoviesType ->
                previousState.copy(moviesType = event.moviesType) to null

            is MoviesEvent.AnticipatedMovies -> previousState.copy(
                isLoading = false,
                anticipatedMovies = event.movies
            ) to null

            is MoviesEvent.TrendingMovies -> previousState.copy(
                isLoading = false,
                trendingMovies = event.movies
            ) to null

            is MoviesEvent.HandleError -> previousState.copy(isLoading = false) to MoviesEffect.ShowErrorMessage(
                event.message
            )
        }
    }
}