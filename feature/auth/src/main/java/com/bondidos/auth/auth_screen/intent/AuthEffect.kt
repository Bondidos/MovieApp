package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class AuthEffect: Reducer.ViewEffect {
    data class ValidationError(val email: Int?, val password: Int?): AuthEffect()
}