package com.bondidos.navigation_api

import kotlinx.serialization.Serializable

interface AppNavigator {
    fun push(screen:AppScreen)
    fun popAndPush(screen:AppScreen)
}

interface AppScreen

@Serializable
object AuthScreen: AppScreen

@Serializable
object MoviesScreen: AppScreen