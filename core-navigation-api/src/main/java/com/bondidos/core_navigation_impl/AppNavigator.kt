package com.bondidos.core_navigation_impl

import kotlinx.serialization.Serializable

interface AppNavigator {
    fun navigateToAuth()

    fun navigateToMovies()
}

@Serializable
object AuthFeature

@Serializable
object MoviesFeature