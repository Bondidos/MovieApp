package com.bondidos.splash

import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class SplashReducer @Inject constructor(): Reducer<SplashState, SplashEvent, SplashEffect> {
    override fun reduce(
        previousState: SplashState,
        event: SplashEvent
    ): Pair<SplashState, SplashEffect?> {
        TODO("Not yet implemented")
    }
}