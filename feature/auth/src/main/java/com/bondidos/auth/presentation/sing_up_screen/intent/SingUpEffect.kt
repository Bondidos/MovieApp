package com.bondidos.auth.presentation.sing_up_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer
import com.bondidos.utils.ValidationResult

@Immutable
sealed class SingUpEffect: Reducer.ViewEffect {
    data class ValidationError(val validationResult: List<ValidationResult?>): SingUpEffect()
}