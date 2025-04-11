package com.bondidos.navigation_impl

import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AppScreen
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder
) : AppNavigator {

    override fun navigate(screen: AppScreen) {
        navControllerHolder.navController.navigate(screen)
    }
}