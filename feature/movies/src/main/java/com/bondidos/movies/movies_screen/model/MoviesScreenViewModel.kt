package com.bondidos.movies.movies_screen.model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.base.UseCaseResult
import com.bondidos.movies.domain.usecase.GetMoviesUseCase
import com.bondidos.movies.domain.usecase.models.GetMoviesParams
import com.bondidos.movies.movies_screen.intent.MoviesEffect
import com.bondidos.movies.movies_screen.intent.MoviesEvent
import com.bondidos.movies.movies_screen.intent.MoviesIntent
import com.bondidos.movies.movies_screen.intent.MoviesState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getMovies: GetMoviesUseCase,
    reducer: MoviesReducer
) : BaseViewModel<MoviesState, MoviesEvent, MoviesEffect>(
    MoviesState.init(),
    reducer
) {

    init {
        appAnalytics.logScreen(ScreenNames.MoviesScreen)

        loadMovies(MovieType.Trending, page = currentState.trendingPage)
        loadMovies(MovieType.Anticipated, page = currentState.anticipatedPage)
    }

    //todo pagination
    override fun emitIntent(intent: Intention) {
        when (intent) {
            is MoviesIntent.ToggleMovies -> {
                val buttonClicked = when(intent.type){
                    MovieType.Anticipated -> ButtonNames.AnticipatedMovies
                    MovieType.Trending -> ButtonNames.AnticipatedMovies
                }
                appAnalytics.logButton(buttonClicked)

                if (currentState.moviesType != intent.type)
                    reduce(MoviesEvent.ToggleMoviesType(intent.type))
            }
        }
    }

    private fun loadMovies(
        type: MovieType,
        page: Int
    ) {
        val requestParams = when(type) {
            MovieType.Anticipated -> GetMoviesParams.GetAnticipated(page = page)
            MovieType.Trending -> GetMoviesParams.GetTrending(page = page)
        }
        viewModelScope.launch(Dispatchers.IO) {
            getMovies.invoke(requestParams)
                .onStart { reduce(MoviesEvent.Loading) }
                .collect { useCaseResult ->

                    when (useCaseResult) {
                        is UseCaseResult.Error -> reduce(
                            MoviesEvent.HandleError(
                                useCaseResult.error.message ?: "UnknownError"
                            )
                        )

                        is UseCaseResult.Success -> reduce(
                            when (type) {
                                MovieType.Trending -> MoviesEvent.TrendingMovies(
                                        useCaseResult.data
                                    )

                                MovieType.Anticipated -> MoviesEvent.AnticipatedMovies(
                                    useCaseResult.data
                                )
                            }
                        )
                    }
                }
        }
    }
}
