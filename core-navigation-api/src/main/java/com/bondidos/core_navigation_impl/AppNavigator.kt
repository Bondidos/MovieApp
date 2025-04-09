package com.bondidos.core_navigation_impl

import kotlinx.serialization.Serializable

interface AppNavigator {
    fun navigate(screen:AppScreen)
}

interface AppScreen

@Serializable
object AuthScreen: AppScreen

@Serializable
object MoviesScreen: AppScreen