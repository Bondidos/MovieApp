package com.bondidos.core_navigation_impl

import androidx.navigation.NavHostController
import com.bondidos.core_navigation_api.AppNavigator

class AppNavigatorImpl(
    private val navController: NavHostController
): AppNavigator {
    override fun navigateToAuth() {
        navController.
    }

    override fun navigateToMovies() {
        TODO("Not yet implemented")
    }

    override fun popBackStack() {
        TODO("Not yet implemented")
    }
}