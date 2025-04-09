package com.bondidos.core_navigation_impl

import com.bondidos.core_navigation_impl.di.NavControllerHolder
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder
) : AppNavigator {

    override fun navigate(screen: AppScreen) {
        navControllerHolder.navController.navigate(screen)
    }
}