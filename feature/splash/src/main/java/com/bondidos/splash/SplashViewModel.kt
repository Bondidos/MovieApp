package com.bondidos.splash

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.domain.usecase.GetCurrentUser
import com.bondidos.base.UseCaseResult
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.navigation_api.MoviesScreen
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appAnalytics: AppAnalytics,
    private val getCurrentUser: GetCurrentUser,
    reducer: SplashReducer,
) :
    BaseViewModel<SplashState, SplashEvent, SplashEffect>(
        SplashState.Init,
        reducer
    ) {
    init {
        appAnalytics.logScreen(ScreenNames.SplashScreen)

        viewModelScope.launch(Dispatchers.IO) {
            getCurrentUser(Unit).collect { result ->
                delay(3000)
                when (result) {
                    is UseCaseResult.Error -> launch(Dispatchers.Main) {
                        appNavigator.popAndPush(AuthScreen)
                    }

                    is UseCaseResult.Success -> {
                        appAnalytics.setUserId(result.data.id)

                        launch(Dispatchers.Main) { appNavigator.popAndPush(MoviesScreen) }
                    }
                }
            }
        }
    }

    override fun emitIntent(intent: Intention) = Unit
}