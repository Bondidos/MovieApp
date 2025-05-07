package com.bondidos.auth.presentation.profile.intent

import com.bondidos.ui.base_mvi.Intention

sealed class ProfileIntent : Intention {
    data object ResetPassword : ProfileIntent()
    data object ChangePassword : ProfileIntent()
    data object DeleteProfile : ProfileIntent()
    data object GoToMovies : ProfileIntent()
    data class OldPasswordChanged(val value: String) : ProfileIntent()
    data class NewPasswordChanged(val value: String) : ProfileIntent()
}