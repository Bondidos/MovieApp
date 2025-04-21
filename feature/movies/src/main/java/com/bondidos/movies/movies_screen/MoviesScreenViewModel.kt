package com.bondidos.movies.movies_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.network.services.TraktApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val traktApiService: TraktApiService //todo to data layer
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = traktApiService.getTrendingMovies()
            print(result)
        }
    }

    //todo refactor to MVI
    fun navigateToMovies() = appNavigator.push(AuthScreen)

    //todo st user id to analytics
}