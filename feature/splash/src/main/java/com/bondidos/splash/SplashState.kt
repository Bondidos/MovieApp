package com.bondidos.splash

import com.bondidos.ui.base_mvi.Reducer

sealed class SplashState: Reducer.ViewState {
    data object Init: SplashState()
}
