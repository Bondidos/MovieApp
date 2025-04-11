package com.bondidos.feature_movies.presentation.movies_screen

import androidx.lifecycle.ViewModel
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    //todo refactor to MVI
    fun navigateToMovies() = appNavigator.navigate(AuthScreen)
}