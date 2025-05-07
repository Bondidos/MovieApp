package com.bondidos.auth.presentation.profile.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.domain.usecase.ChangePasswordUseCase
import com.bondidos.auth.domain.usecase.DeleteProfileUseCase
import com.bondidos.auth.domain.usecase.GetCurrentUser
import com.bondidos.auth.domain.usecase.ResetPasswordUseCase
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileEvent
import com.bondidos.auth.presentation.profile.intent.ProfileIntent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.base.UseCaseResult
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val analytics: AppAnalytics,
    private val getCurrentUser: GetCurrentUser,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    reducer: AuthReducer,
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(
    ProfileState.init(),
    reducer
) {
    init {
        analytics.logScreen(ScreenNames.ProfileScreen)

        viewModelScope.launch(Dispatchers.IO) {
            getCurrentUser()
                .onStart { reduce(ProfileEvent.Loading) }
                .onCompletion { reduce(ProfileEvent.Loaded) }
                .collect { currentUser ->
                    when (currentUser) {
                        is UseCaseResult.Success -> {
                            currentUser.data.email?.let {
                                reduce(ProfileEvent.UserData(it))
                            } ?: reduce(ProfileEvent.Error("Unknown Error"))
                        }

                        is UseCaseResult.Error -> reduce(
                            ProfileEvent.Error(
                                currentUser.error.message ?: "Unknown Error"
                            )
                        )
                    }

                }
        }
    }

    override fun emitIntent(intent: Intention) {
        when (intent) {
            ProfileIntent.ResetPassword -> {
                TODO()
            }

            ProfileIntent.DeleteProfile -> {
                TODO()
            }
        }
    }
}
