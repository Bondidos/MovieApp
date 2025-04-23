package com.bondidos.movies.movies_screen.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.movies.domain.usecase.GetMoviesUseCase
import com.bondidos.movies.domain.usecase.models.GetMoviesParams
import com.bondidos.movies.movies_screen.intent.MoviesEffect
import com.bondidos.movies.movies_screen.intent.MoviesEvent
import com.bondidos.movies.movies_screen.intent.MoviesIntent
import com.bondidos.movies.movies_screen.intent.MoviesState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val startingPage = 1 //todo pagination

    init {
        appAnalytics.logScreen(ScreenNames.MoviesScreen)

//        viewModelScope.launch(Dispatchers.IO) {
//            getMovies.invoke(
//                GetMoviesParams.GetTrending(page = startingPage)
//            )
//                .collect {
//                    it
//                }
//        }
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            is MoviesIntent.ToggleMovies -> {
                reduce(MoviesEvent.Loading)
                // todo call for content

                if (currentState.moviesType != intent.type)
                    reduce(MoviesEvent.ToggleMoviesType(intent.type))
            }
        }
    }
}