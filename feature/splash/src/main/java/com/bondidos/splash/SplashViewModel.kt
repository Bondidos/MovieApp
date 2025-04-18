package com.bondidos.splash

import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val analytics: AppAnalytics,
    reducer: SplashReducer,
) :
    BaseViewModel<SplashState, SplashEvent, SplashEffect>(
        SplashState.Init,
        reducer
    ) {
    init {
        analytics.logScreen(ScreenNames.SplashScreen)
    }

    override fun emitIntent(intent: Intention) = Unit
}