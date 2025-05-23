package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class ProfileEffect: Reducer.ViewEffect {
    data class HandleError(val message: String): ProfileEffect()
    data object ShowConfirmPassword: ProfileEffect()
    data object ShowResetPasswordSuccess: ProfileEffect()
    data object ShowResetPasswordFailure: ProfileEffect()
    data object ChangePassword: ProfileEffect()
    data object PasswordChanged: ProfileEffect()
}