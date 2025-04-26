package com.bondidos.movies.presentation.movies_screen.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.domain.usecase.SingOutUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.movies.domain.model.Movie
import com.bondidos.movies.domain.usecase.GetMoviesUseCase
import com.bondidos.movies.domain.usecase.models.GetMoviesParams
import com.bondidos.movies.presentation.movies_screen.intent.MoviesEffect
import com.bondidos.movies.presentation.movies_screen.intent.MoviesEvent
import com.bondidos.movies.presentation.movies_screen.intent.MoviesIntent
import com.bondidos.movies.presentation.movies_screen.intent.MoviesState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.navigation_api.MovieDetailsScreen
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.composables.MovieType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getMovies: GetMoviesUseCase,
    private val singOut: SingOutUseCase,
    reducer: MoviesReducer
) : BaseViewModel<MoviesState, MoviesEvent, MoviesEffect>(
    MoviesState.init(),
    reducer
) {
    init {
        appAnalytics.logScreen(ScreenNames.MoviesScreen)

        loadTrendingAndAnticipated()
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            is MoviesIntent.ToggleMovies -> {
                val buttonClicked = when (intent.type) {
                    MovieType.Anticipated -> ButtonNames.AnticipatedMovies
                    MovieType.Trending -> ButtonNames.TrendingMovies
                }
                appAnalytics.logButton(buttonClicked)

                if (currentState.moviesType != intent.type)
                    reduce(MoviesEvent.ToggleMoviesType(intent.type))
            }

            MoviesIntent.NextTrendingPage -> {
                reduce(MoviesEvent.IncrementTrendingPage)
                loadMoviesPage(currentState.moviesType, currentState.trendingPage)
            }

            MoviesIntent.NextAnticipatedPage -> {
                reduce(MoviesEvent.IncrementAnticipatedPage)
                loadMoviesPage(currentState.moviesType, currentState.anticipatedPage)
            }

            MoviesIntent.SingOut -> {
                appAnalytics.logButton(ButtonNames.SingOut)
                viewModelScope.launch {
                    singOut().collect {
                        appNavigator.popAndPush(AuthScreen)
                    }
                }
            }

            MoviesIntent.Refresh -> {
                // Just reload from Repository, but also possible to drop cache and load from remote
                reduce(MoviesEvent.Refresh)
                loadTrendingAndAnticipated()
            }

            is MoviesIntent.ShowDetails -> {
                appAnalytics.logMovie(intent.id)

                appNavigator.push(
                    MovieDetailsScreen(
                        intent.id,
                        currentState.moviesType.toString(),
                        when (currentState.moviesType) {
                            is MovieType.Trending -> currentState.trendingPage
                            is MovieType.Anticipated -> currentState.anticipatedPage
                        }
                    )
                )
            }

            MoviesIntent.NavigateToProfile -> {
                TODO()
            }
        }
    }

    private fun loadTrendingAndAnticipated() {
        val trendingFlow =
            getMovies.invoke(GetMoviesParams.GetTrending(page = currentState.trendingPage))
        val anticipatedFlow =
            getMovies.invoke(GetMoviesParams.GetAnticipated(page = currentState.anticipatedPage))

        viewModelScope.launch(Dispatchers.IO) {
            trendingFlow.zip(
                other = anticipatedFlow,
                transform = { trending, anticipated -> trending to anticipated }
            )
                .onStart { reduce(MoviesEvent.Loading) }
                .collect(::handleMoviesCollected)
        }
    }

    private fun handleMoviesCollected(value: Pair<UseCaseResult<List<Movie>>, UseCaseResult<List<Movie>>>) {
        val (trending, anticipated) = value
        if (trending is UseCaseResult.Error) reduce(
            MoviesEvent.HandleError(
                trending.error.message ?: "UnknownError"
            )
        )

        if (anticipated is UseCaseResult.Error) reduce(
            MoviesEvent.HandleError(
                anticipated.error.message ?: "UnknownError"
            )
        )

        if (trending is UseCaseResult.Success)
            reduce(MoviesEvent.TrendingMovies(trending.data))

        if (anticipated is UseCaseResult.Success)
            reduce(MoviesEvent.AnticipatedMovies(anticipated.data))

        reduce(MoviesEvent.Loaded)
    }

    private fun loadMoviesPage(
        type: MovieType,
        page: Int
    ) {
        val requestParams = when (type) {
            MovieType.Anticipated -> GetMoviesParams.GetAnticipated(page = page)
            MovieType.Trending -> GetMoviesParams.GetTrending(page = page)
        }
        viewModelScope.launch(Dispatchers.IO) {
            getMovies.invoke(requestParams)
                .collect { useCaseResult ->
                    when (useCaseResult) {
                        is UseCaseResult.Error -> reduce(
                            MoviesEvent.HandleError(
                                useCaseResult.error.message ?: "UnknownError"
                            )
                        )

                        is UseCaseResult.Success -> {
                            reduce(
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
}
