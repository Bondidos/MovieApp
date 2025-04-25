package com.bondidos.movies.presentation.movie_details_screen.model

import androidx.lifecycle.SavedStateHandle
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEffect
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsEvent
import com.bondidos.movies.presentation.movie_details_screen.intent.MovieDetailsState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    reducer: MovieDetailsReducer,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<MovieDetailsState, MovieDetailsEvent, MovieDetailsEffect>(
    MovieDetailsState.init(),
    reducer
) {
    init {
        val movieId = savedStateHandle.get<Int?>("movieId")
        appAnalytics.logScreen(ScreenNames.MoviesScreen)
    }

    override fun emitIntent(intent: Intention) {

    }
}
