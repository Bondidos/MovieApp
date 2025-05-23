package com.bondidos.movies.presentation.movies_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.movies.domain.model.movie.Movie
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieType

@Immutable
sealed class MoviesEvent : Reducer.ViewEvent {
    data object Loading : MoviesEvent()
    data object Loaded : MoviesEvent()
    data object Refresh : MoviesEvent()
    data class ToggleMoviesType(val moviesType: MovieType) : MoviesEvent()
    data class TrendingMovies(val movies: List<Movie>) : MoviesEvent()
    data class AnticipatedMovies(val movies: List<Movie>) : MoviesEvent()
    data class HandleError(val message: String) : MoviesEvent()
    data object IncrementTrendingPage : MoviesEvent()
    data object IncrementAnticipatedPage : MoviesEvent()
}