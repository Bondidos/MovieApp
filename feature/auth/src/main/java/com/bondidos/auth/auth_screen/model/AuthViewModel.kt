package com.bondidos.auth.auth_screen.model

import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthIntent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.ui.validators.EmailValidator
import com.bondidos.ui.validators.PasswordValidator
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
    // TODO EVENTS TO FIREBASE
    override fun emitIntent(intent: Intention) {
        when (intent) {
            is AuthIntent.Login -> {
                //todo LOGIN
            }

            is AuthIntent.SignIn -> {
                //todo Navigate
            }

            is AuthIntent.LoginWithGoogle -> {}

            is AuthIntent.PasswordChanged -> {
                //todo validate
                reduce(AuthEvent.PasswordChanged(intent.value))
            }
            is AuthIntent.EmailChanged -> {
                //todo validate
                reduce(AuthEvent.EmailChanged(intent.value))
            }
        }
    }

    private fun validateForm(): AuthEvent? {
        val emailError = EmailValidator.validateEmail(currentState.emailValue)
        val passwordError =
            PasswordValidator.validatePassword(currentState.passwordValue)
        return if (emailError != null || passwordError != null) AuthEvent.ValidationError(
            emailMessageRes = emailError,
            passwordMessageRes = passwordError
        ) else null

    }

}
