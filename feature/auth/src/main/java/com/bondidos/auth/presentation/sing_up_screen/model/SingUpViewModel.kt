package com.bondidos.auth.presentation.sing_up_screen.model

import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpEffect
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpEvent
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpIntent
import com.bondidos.auth.presentation.sing_up_screen.intent.SingUpState
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import com.bondidos.utils.AppValidator
import com.bondidos.utils.SingInFormValidationResult
import com.bondidos.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val appValidator: AppValidator,
    private val analytics: AppAnalytics,
    reducer: SingUpReducer,
) : BaseViewModel<SingUpState, SingUpEvent, SingUpEffect>(
    SingUpState.init(),
    reducer
) {
    init {
        analytics.logScreen(
            ScreenNames.SingUpScreen
        )
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            SingUpIntent.CreateAccount -> {
                analytics.logButton(ButtonNames.CreateAccount)

                if(validateForm()){

                }
            }

            is SingUpIntent.EmailChanged -> reduce(SingUpEvent.EmailChanged(intent.value))
            is SingUpIntent.PasswordChanged -> reduce(SingUpEvent.PasswordChanged(intent.value))
            is SingUpIntent.PasswordRetypedChanged -> reduce(
                SingUpEvent.PasswordRetypedChanged(
                    intent.value
                )
            )
        }
    }

    private fun validateForm(): Boolean {
        val validationResult = appValidator.validateSingUpForm(
            email = currentState.emailValue,
            password = currentState.passwordValue,
            passwordRetyped = currentState.passwordRetypedValue
        )
        return handleValidationErrors(validationResult)
    }

    private fun handleValidationErrors(validationResult: SingInFormValidationResult): Boolean {
        if (validationResult.isFormValid()) return true

        val validationSnackBarBuilder = SingUpEvent.ShowValidationErrorSnackBar.builder();
        val invalidFields = mutableListOf<String>()

        handleEmail(validationResult.email)?.let { email ->
            invalidFields.add(email.toString())
            validationSnackBarBuilder.setEmail(email)
        }

        handlePassword(validationResult.password)?.let { password ->
            invalidFields.add(password.toString())
            validationSnackBarBuilder.setPassword(password)
        }

        handlePasswordRetyped(validationResult.passwordRetyped)?.let { passwordRetyped ->
            reduce(
                SingUpEvent.PasswordRetypedValidationError(
                    passwordRetyped
                )
            )

            invalidFields.add(passwordRetyped.toString())
            validationSnackBarBuilder.setPasswordRetyped(passwordRetyped)
        }

        analytics.logValidation(invalidFields)
        reduce(validationSnackBarBuilder.build())
        return false
    }

    private fun handleEmail(email: ValidationResult.EmailValidationResult): ValidationResult? {
        if (email.isEmailValid()) return null
        reduce(
            SingUpEvent.EmailValidationError(email)
        )
        return email
    }

    private fun handlePassword(password: ValidationResult.PasswordValidationResult): ValidationResult? {
        if (password.isPasswordValid()) return null
        reduce(
            SingUpEvent.PasswordValidationError(password)
        )
        return password
    }

    private fun handlePasswordRetyped(passwordRetyped: ValidationResult.PasswordValidationResult): ValidationResult? {
        if (passwordRetyped.isPasswordValid()) return null
        reduce(
            SingUpEvent.PasswordRetypedValidationError(passwordRetyped)
        )
        return passwordRetyped
    }
}
