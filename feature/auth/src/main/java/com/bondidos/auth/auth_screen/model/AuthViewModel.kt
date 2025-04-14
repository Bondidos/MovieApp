package com.bondidos.auth.auth_screen.model

import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthIntent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.utils.AppValidator
import com.bondidos.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appValidator: AppValidator,
    reducer: AuthReducer,
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(
    AuthState.init(),
    reducer
) {
    // TODO EVENTS TO FIREBASE
    override fun emitIntent(intent: Intention) {
        when (intent) {
            is AuthIntent.Login -> {
                validateAndUpdate()
                if (currentState.isFormValid()) {
                    TODO("loginLogic")
                }
            }

            is AuthIntent.SignIn -> {
                TODO()
            }

            is AuthIntent.LoginWithGoogle -> {
                TODO()
            }

            is AuthIntent.PasswordChanged -> {
                reduce(AuthEvent.PasswordChanged(intent.value))
            }

            is AuthIntent.EmailChanged -> {
                reduce(AuthEvent.EmailChanged(intent.value))
            }
        }
    }

    private fun validateAndUpdate() {
        val passwordValue = validate(
            currentState.passwordValue,
            appValidator::validatePassword
        ).also { result ->
            if (!result.isPasswordValid()) reduce(AuthEvent.PasswordValidationError(result))
        }
        val emailValue = validate(
            currentState.emailValue,
            appValidator::validateEmail
        ).also { result ->
            if (!result.isEmailValid()) reduce(AuthEvent.EmailValidationError(result))
        }
        if (!currentState.isFormValid())
            reduce(
                AuthEvent.ShowValidationErrorSnackBar(
                    email = if (currentState.isEmailError) emailValue else null,
                    password = if (currentState.isPasswordValueError) passwordValue else null,
                )
            )
    }

    private fun validate(value: String, validation: (String) -> ValidationResult) =
        validation(value)
}
