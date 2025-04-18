package com.bondidos.navigation_impl

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AppScreen
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder
) : AppNavigator {

    @MainThread
    override fun push(screen: AppScreen) {
        navControllerHolder.navController.navigate(screen)
    }

    @MainThread
    override fun popAndPush(screen: AppScreen) {
        navControllerHolder.navController.apply {
            popBackStack()
            navigate(screen)
        }
    }

    @SuppressLint("RestrictedApi")
    @MainThread
    override fun popAllAndPush(screen: AppScreen) {
        navControllerHolder.navController.apply {
            currentBackStack.value.forEach { _ ->
                popBackStack()
            }
            navigate(screen)
        }
    }
}