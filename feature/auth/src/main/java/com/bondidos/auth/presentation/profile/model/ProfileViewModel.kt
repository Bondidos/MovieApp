package com.bondidos.auth.presentation.profile.model

import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileEvent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val analytics: AppAnalytics,
    reducer: AuthReducer,
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(
    ProfileState.init(),
    reducer
) {
    init {
        analytics.logScreen(
            ScreenNames.ProfileScreen
        )
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {

        }
    }
}
