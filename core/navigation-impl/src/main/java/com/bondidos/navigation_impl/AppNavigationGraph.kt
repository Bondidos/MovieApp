package com.bondidos.navigation_impl

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.navigation_api.MovieDetailsScreen
import com.bondidos.navigation_api.MoviesScreen
import com.bondidos.navigation_api.SingUpScreen
import com.bondidos.navigation_api.SplashScreen

@Composable
fun AppNavigationGraph(
    authScreen: @Composable () -> Unit,
    singUpScreen: @Composable () -> Unit,
    moviesScreen: @Composable () -> Unit,
    splashScreen: @Composable () -> Unit,
    movieDetailsScreen: @Composable () -> Unit,
    navControllerHolder: NavControllerHolder,
) {

    val navController = rememberNavController()
    navControllerHolder.navController = navController

    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {
        composable<AuthScreen> { authScreen() }
        composable<MoviesScreen> { moviesScreen() }
        composable<SingUpScreen> { singUpScreen() }
        composable<SplashScreen> { splashScreen() }
        composable<MovieDetailsScreen> { movieDetailsScreen() }
    }
}