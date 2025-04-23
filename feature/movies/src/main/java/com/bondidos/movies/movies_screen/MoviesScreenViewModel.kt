package com.bondidos.movies.movies_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.movies.domain.usecase.GetMoviesUseCase
import com.bondidos.movies.domain.usecase.modles.GetMoviesParams
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getMovies: GetMoviesUseCase,
    ) : ViewModel() {

    init {

        viewModelScope.launch(Dispatchers.IO) {
            getMovies.invoke(
                GetMoviesParams.GetTrending(page = 1)
            )
                .collect {
                    it
                }
        }
    }

    //todo refactor to MVI
    fun navigateToMovies() = appNavigator.push(AuthScreen)

    //todo st user id to analytics
}