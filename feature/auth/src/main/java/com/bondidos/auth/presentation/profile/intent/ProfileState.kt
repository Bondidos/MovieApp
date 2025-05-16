package com.bondidos.auth.presentation.profile.intent

import androidx.compose.runtime.Immutable
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.ui.base_mvi.Reducer

@Immutable
data class ProfileState(
    val isLoading: Boolean,
    val email: String,
    val isPasswordsNotSame: Boolean,
    val oldPasswordValue: String,
    val newPasswordValue: String,
    val signInMethod: AuthUser.SignInMethod,
) : Reducer.ViewState {

    companion object {
        fun init() = ProfileState(
            isLoading = false,
            email = "",
            isPasswordsNotSame = false,
            oldPasswordValue = "",
            newPasswordValue = "",
            signInMethod = AuthUser.SignInMethod.Unknown
        )
    }
}

enum class BottomSheetType {
    None,
    DeleteProfile,
    ResetPasswordFailure,
    ResetPasswordSuccess,
    ChangePaswword
}