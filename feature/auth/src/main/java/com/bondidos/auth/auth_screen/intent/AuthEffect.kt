package com.bondidos.auth.auth_screen.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class AuthEffect: Reducer.ViewEffect {
    //todo Snackbars etc (NAVIGATION???? - no. in user events and VM)
    data class SomeError(val message: String): AuthEffect()
}