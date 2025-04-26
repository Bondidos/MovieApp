package com.bondidos.navigation_api

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

interface AppNavigator {
    fun push(screen: AppScreen)
    fun popAndPush(screen: AppScreen)
    fun popAllAndPush(screen: AppScreen)
}

interface AppScreen

@Serializable
object AuthScreen : AppScreen

@Serializable
object MoviesScreen : AppScreen

@Serializable
object SingUpScreen : AppScreen

@Serializable
object SplashScreen : AppScreen

@Serializable
@Keep
data class MovieDetailsScreen(
    val movieId: Int?,
    val movieType: String,
    val page: Int,
    ) : AppScreen