package com.bondidos.navigation_impl

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.navigation_api.MoviesScreen

@Composable
fun AppNavigationGraph(
    authFeature: @Composable () -> Unit,
    moviesFeature: @Composable () -> Unit,
    navControllerHolder: NavControllerHolder,
) {


    val navController = rememberNavController()
    navControllerHolder.navController = navController

    NavHost(
        navController = navController,
        startDestination = AuthScreen
    ) {
        composable<AuthScreen> { authFeature() }
        composable<MoviesScreen> { moviesFeature() }
    }
}