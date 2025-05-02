package com.bondidos.movies.presentation.movie_details_screen.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.base.UseCaseResult
import com.bondidos.movies.domain.usecase.GetCrewAndCast
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getCrewAndCast: GetCrewAndCast,
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
            // TODO("Fetch Cast")
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetailsUseCase.invoke(params)
                .onStart { reduce(MovieDetailsEvent.Loading) }
                .collect { result ->
                    when (result) {
                        is UseCaseResult.Error -> reduce(
                            MovieDetailsEvent.HandleError(
                                result.error.message ?: "Unknown Error"
                            )
                        )

                        is UseCaseResult.Success -> reduce(MovieDetailsEvent.Loaded(result.data))
                    }
                }

            //todo test zone
            getCrewAndCast.invoke(params.traktId!!).collect{
                it
                // todo cache and error catching
            }
        }
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            MovieDetailsIntent.GoBack -> {
                appAnalytics.logButton(ButtonNames.GoBack)

                appNavigator.pop()
            }

            MovieDetailsIntent.PlayTrailer -> {
                TODO("Play trailer")
            }

            MovieDetailsIntent.ShareTrailerLink -> {
                TODO("Share")
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

    companion object {
        const val MOVIE_ID_KEY = "movieId"
        const val MOVIE_TYPE_KEY = "movieType"
        const val PAGE_KEY = "page"
    }
}
