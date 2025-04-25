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
            is MoviesEvent.Loaded -> previousState.copy(isLoading = false, refreshing = false) to null
            is MoviesEvent.ToggleMoviesType ->
                previousState.copy(moviesType = event.moviesType) to null

            is MoviesEvent.AnticipatedMovies -> previousState.copy(
                anticipatedMovies = previousState.anticipatedMovies + event.movies
            ) to null

            is MoviesEvent.TrendingMovies -> previousState.copy(
                trendingMovies = previousState.trendingMovies + event.movies
            ) to null

            is MoviesEvent.HandleError -> previousState to MoviesEffect.ShowErrorMessage(
                event.message
            )

            MoviesEvent.IncrementAnticipatedPage -> previousState.copy(
                anticipatedPage = previousState.anticipatedPage.inc()
            ) to null

            MoviesEvent.IncrementTrendingPage -> previousState.copy(
                trendingPage = previousState.trendingPage.inc()
            ) to null

            is MoviesEvent.Refresh -> previousState.copy(refreshing = true) to null
        }
    }
}