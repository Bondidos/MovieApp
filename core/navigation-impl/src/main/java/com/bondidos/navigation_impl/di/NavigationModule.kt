package com.bondidos.navigation_impl.di

import androidx.navigation.NavHostController
import com.bondidos.navigation_impl.AppNavigatorImpl
import com.bondidos.navigation_api.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class NavControllerHolder {
    lateinit var navController: NavHostController
}

@Module(includes = [NavigationModuleBinds::class])
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun navController() = NavControllerHolder()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModuleBinds {
    @Binds
    abstract fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
}