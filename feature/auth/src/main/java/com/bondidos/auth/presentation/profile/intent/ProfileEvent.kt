package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.ui.base_mvi.Reducer

@Immutable
sealed class ProfileEvent : Reducer.ViewEvent {
    data object Loading : ProfileEvent()
    data object Loaded : ProfileEvent()
    data class UserData(
        val email: String,
        val singInMethod: AuthUser.SignInMethod
    ) : ProfileEvent()

    data class Error(val message: String) : ProfileEvent()
    data class OldPasswordChanged(val value: String) : ProfileEvent()
    data class NewPasswordChanged(val value: String) : ProfileEvent()
    data object ShowConfirmProfileDelete : ProfileEvent()
    data class InvalidPassword(val message: String) : ProfileEvent()
    data object ResetPasswordFailure : ProfileEvent()
    data object ResetPasswordSuccess : ProfileEvent()
}
