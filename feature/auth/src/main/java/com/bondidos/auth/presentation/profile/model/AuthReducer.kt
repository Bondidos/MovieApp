package com.bondidos.auth.presentation.profile.model

import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileEvent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.ui.base_mvi.Reducer
import javax.inject.Inject

class AuthReducer @Inject constructor() : Reducer<ProfileState, ProfileEvent, ProfileEffect> {
    override fun reduce(
        previousState: ProfileState,
        event: ProfileEvent
    ): Pair<ProfileState, ProfileEffect?> {
        return when (event) {
            ProfileEvent.Loading -> previousState.copy(isLoading = true) to null
            is ProfileEvent.Error -> previousState to ProfileEffect.HandleError(event.message)
            ProfileEvent.Loaded -> previousState.copy(isLoading = false) to null
            is ProfileEvent.UserData -> previousState.copy(email = event.email) to null
            is ProfileEvent.NewPasswordChanged -> previousState.copy(
                newPasswordValue = event.value
            ) to null

            is ProfileEvent.OldPasswordChanged -> previousState.copy(
                oldPasswordValue = event.value
            ) to null
        }
    }
}