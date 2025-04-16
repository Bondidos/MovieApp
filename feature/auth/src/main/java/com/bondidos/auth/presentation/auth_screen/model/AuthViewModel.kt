package com.bondidos.auth.presentation.auth_screen.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.presentation.auth_screen.intent.AuthEffect
import com.bondidos.auth.presentation.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.intent.AuthIntent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.auth.domain.usecase.LoginUseCase
import com.bondidos.base.UseCaseResult
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.MoviesScreen
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.utils.AppValidator
import com.bondidos.utils.FormValidationResult
import com.bondidos.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appValidator: AppValidator,
    private val analytics: AppAnalytics,
    private val loginUseCase: LoginUseCase,
    reducer: AuthReducer,
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(
    AuthState.init(),
    reducer
) {
    init {
        analytics.logScreen(
            ScreenNames.AuthScreen
        )
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            is AuthIntent.Login -> {
                analytics.logButton(ButtonNames.Login)

                validateAndReduce()
                login()
            }

            is AuthIntent.SignIn -> {
                analytics.logButton(ButtonNames.SingUp)
                TODO()
            }

            is AuthIntent.LoginWithGoogle -> {
                analytics.logButton(ButtonNames.LoginWithGoogle)
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

    private fun login() {
        if (currentState.isFormValid()) {
            val loginParams = currentState.emailValue to currentState.passwordValue
            viewModelScope.launch(Dispatchers.IO) {
                loginUseCase.invoke(loginParams)
                    .onStart { reduce(AuthEvent.Loading) }
                    .collect { useCaseResult ->
                        when (useCaseResult) {
                            is UseCaseResult.Success -> {
                                /// Set UserId for analytics
                                analytics.setUserId(useCaseResult.data.id)

                                launch(Dispatchers.Main) {
                                    appNavigator.popAndPush(MoviesScreen)
                                }
                            }

                            is UseCaseResult.Error -> {
                                reduce(
                                    AuthEvent.AuthError(
                                        useCaseResult.error.message
                                    )
                                )
                            }
                        }

                    }

            }
        }
    }

    private fun validateAndReduce() {
        val (emailValidation, passwordValidation) = validateFormFields()

        if (!emailValidation.isEmailValid() || !passwordValidation.isPasswordValid()) {
            handleValidationResults(emailValidation to passwordValidation)
        }
    }

    private fun validateFormFields(): FormValidationResult {
        return appValidator.validateLoginForm(
            currentState.emailValue,
            currentState.passwordValue
        ).also { (email, password) ->
            if (!email.isEmailValid()) reduce(AuthEvent.EmailValidationError(email))
            if (!password.isPasswordValid()) reduce(AuthEvent.PasswordValidationError(password))
        }
    }

    private fun handleValidationResults(
        formValidationResult: FormValidationResult
    ) {
        val invalidFields = listOfNotNull(
            formValidationResult.first.takeUnless { it.isEmailValid() },
            formValidationResult.second.takeUnless { it.isPasswordValid() }
        )

        logInvalidFields(invalidFields)
        showErrorSnackbar(invalidFields)
    }

    private fun logInvalidFields(fields: List<ValidationResult>) {
        analytics.logValidation(fields.map(ValidationResult::toString))
    }

    private fun showErrorSnackbar(fields: List<ValidationResult>) {
        reduce(
            AuthEvent.ShowValidationErrorSnackBar(
                email = fields.find { it is ValidationResult.EmailValidationResult },
                password = fields.find { it is ValidationResult.PasswordValidationResult }
            )
        )
    }
}
