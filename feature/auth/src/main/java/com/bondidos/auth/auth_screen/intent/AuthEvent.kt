package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.utils.ValidationResult

@Immutable
sealed class AuthEvent : Reducer.ViewEvent {
    data object Loading : AuthEvent()
    data class EmailChanged(val value: String) : AuthEvent()
    data class EmailValidationError(val validationResult: ValidationResult) : AuthEvent()
    data class PasswordChanged(val value: String) : AuthEvent()
    data class PasswordValidationError(val validationResult: ValidationResult) : AuthEvent()
    data class ShowValidationErrorSnackBar(
        val email: ValidationResult?,
        val password: ValidationResult?
    ) : AuthEvent()
}
