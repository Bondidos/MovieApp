package com.bondidos.navigation_api

import kotlinx.serialization.Serializable

interface AppNavigator {
    fun navigate(screen:AppScreen)
}

interface AppScreen

@Serializable
object AuthScreen: AppScreen

@Serializable
object MoviesScreen: AppScreen