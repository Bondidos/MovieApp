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
                TODO()
            }

            is AuthIntent.SignIn -> {
                TODO()
            }

            is AuthIntent.LoginWithGoogle -> {
                TODO()
            }

            is AuthIntent.PasswordChanged -> {
                val validationResult = validate(intent.value, appValidator::validatePassword)
                if (validationResult.isPasswordValid())
                    reduce(AuthEvent.PasswordChanged(intent.value))
                else reduce(AuthEvent.PasswordValidationError(validationResult))
            }

            is AuthIntent.EmailChanged -> {
                val validationResult = validate(intent.value, appValidator::validateEmail)
                if (validationResult.isEmailValid())
                    reduce(AuthEvent.EmailChanged(intent.value))
                else reduce(AuthEvent.EmailValidationError(validationResult))
            }
        }
    }

    private fun validate(value: String, validation: (String) -> ValidationResult) =
        validation(value)
}
