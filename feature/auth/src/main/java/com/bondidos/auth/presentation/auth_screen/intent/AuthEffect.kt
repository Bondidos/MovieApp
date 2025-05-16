package com.bondidos.auth.presentation.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.utils.ValidationResult

@Immutable
sealed class AuthEffect: Reducer.ViewEffect {
    data class ValidationError(val validationResult: List<ValidationResult?>): AuthEffect()
    data class AuthError(val message: String): AuthEffect()
}