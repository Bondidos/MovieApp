package com.bondidos.movies.presentation.movie_details_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.movies.domain.model.movie.MovieDetails
import com.bondidos.movies.domain.model.people.PeopleWithImage
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.ui.composables.MovieDetailsType

@Immutable
sealed class MovieDetailsEvent : Reducer.ViewEvent {
    data object Loading : MovieDetailsEvent()
    data object Loaded : MovieDetailsEvent()
    data class DetailsLoaded(val moveDetails: MovieDetails) : MovieDetailsEvent()
    data class CrewAndCastLoaded(val crewAndCast: List<PeopleWithImage>) : MovieDetailsEvent()
    data class HandleError(val message: String) : MovieDetailsEvent()
    data class ChangeDetailsType(val type: MovieDetailsType) : MovieDetailsEvent()
}
