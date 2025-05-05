package com.bondidos.movies.presentation.movie_details_screen.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.base.UseCaseResult
import com.bondidos.movies.domain.model.movie.MovieDetails
import com.bondidos.movies.domain.model.people.PeopleWithImage
import com.bondidos.movies.domain.usecase.GetCrewAndCastUseCase
import com.bondidos.movies.domain.usecase.GetMovieDetailsUseCase
import com.bondidos.movies.domain.usecase.models.GetMovieDetailsParams
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEvent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsIntent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieDetailsType
import com.bondidos.ui.composables.MovieType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCrewAndCast: GetCrewAndCastUseCase,
    reducer: MovieDetailsReducer,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect>(
    MovieDetailsState.init(),
    reducer
) {
    init {
        appAnalytics.logScreen(ScreenNames.MovieDetailsScreen)

        val params = GetMovieDetailsParams(
            savedStateHandle[MOVIE_ID_KEY],
            MovieType.fromString(savedStateHandle[MOVIE_TYPE_KEY]),
            savedStateHandle[PAGE_KEY] ?: -1,
        )
        viewModelScope.launch(Dispatchers.IO) {
            params.traktId?.let {
                getMovieDetailsUseCase(params).zip(
                    getCrewAndCast(it),
                    transform = { movies, cast -> movies to cast }
                )
                    .onStart { reduce(MovieDetailsEvent.Loading) }
                    .collect(::handleOnDetailsReceived)
            }
        }
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            MovieDetailsIntent.GoBack -> {
                appAnalytics.logButton(ButtonNames.GoBack)

                appNavigator.pop()
            }

            MovieDetailsIntent.PlayTrailer -> reduce(MovieDetailsEvent.PlayTrailer)

            MovieDetailsIntent.ShareTrailerLink -> reduce(MovieDetailsEvent.ShareMovie)

            MovieDetailsIntent.ShowAllCastAndCrew -> {
                //todo Navigate to Show all cast and crew
            }

            is MovieDetailsIntent.MovieDetailsTypeChanged -> {
                val buttonType = when (intent.type) {
                    MovieDetailsType.Detail -> ButtonNames.Details
                    MovieDetailsType.Reviews -> ButtonNames.Reviews
                    MovieDetailsType.Showtime -> ButtonNames.Showtime
                }
                appAnalytics.logButton(buttonType)

                if (currentState.detailsType != intent.type)
                    reduce(MovieDetailsEvent.ChangeDetailsType(intent.type))
            }
        }
    }

    private fun handleOnDetailsReceived(
        value: Pair<UseCaseResult<MovieDetails>, UseCaseResult<List<PeopleWithImage>>>
    ) {
        val (movieDetails, crewAndCast) = value
        when (movieDetails) {
            is UseCaseResult.Success -> reduce(
                MovieDetailsEvent.DetailsLoaded(
                    movieDetails.data
                )
            )

            is UseCaseResult.Error -> reduce(
                MovieDetailsEvent.HandleError(
                    movieDetails.error.message ?: "Unknown Error"
                )
            )
        }

        when (crewAndCast) {
            is UseCaseResult.Success -> reduce(
                MovieDetailsEvent.CrewAndCastLoaded(
                    crewAndCast.data
                )
            )

            is UseCaseResult.Error -> reduce(
                MovieDetailsEvent.HandleError(
                    crewAndCast.error.message ?: "Unknown Error"
                )
            )
        }

        reduce(MovieDetailsEvent.Loaded)
    }

    companion object {
        const val MOVIE_ID_KEY = "movieId"
        const val MOVIE_TYPE_KEY = "movieType"
        const val PAGE_KEY = "page"
    }
}
