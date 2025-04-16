package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.utils.ValidationResult

@Immutable
sealed class AuthEffect: Reducer.ViewEffect {
    data class ValidationError(val validationResult: List<ValidationResult?>): AuthEffect()
}