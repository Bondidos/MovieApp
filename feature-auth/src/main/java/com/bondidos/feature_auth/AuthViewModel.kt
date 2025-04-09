package com.bondidos.feature_auth

import androidx.lifecycle.ViewModel
import com.bondidos.core_navigation_impl.AppNavigator
import com.bondidos.core_navigation_impl.MoviesScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {
    //todo refactor to MVI
    fun navigateToMovies() = appNavigator.navigate(MoviesScreen)
}
