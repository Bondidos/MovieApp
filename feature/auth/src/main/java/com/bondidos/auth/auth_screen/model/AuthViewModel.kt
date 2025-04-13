package com.bondidos.auth.auth_screen.model

import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    reducer: AuthReducer,
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(
    AuthState.init(),
    reducer
) {
    init {
        // Get data via UseCases
    }

    //todo some logic =)
}
