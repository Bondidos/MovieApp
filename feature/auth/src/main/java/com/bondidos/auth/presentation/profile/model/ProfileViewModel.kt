package com.bondidos.auth.presentation.profile.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.domain.usecase.ChangePasswordUseCase
import com.bondidos.auth.domain.usecase.DeleteProfileUseCase
import com.bondidos.auth.domain.usecase.GetCurrentUser
import com.bondidos.auth.domain.usecase.ResetPasswordUseCase
import com.bondidos.auth.domain.usecase.model.ChangePasswordParams
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileEvent
import com.bondidos.auth.presentation.profile.intent.ProfileIntent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.base.UseCaseResult
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.MoviesScreen
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
                analytics.logButton(ButtonNames.ResetPassword)

                viewModelScope.launch(Dispatchers.IO) {
                    resetPasswordUseCase()
                        .onStart { reduce(ProfileEvent.Loading) }
                        .collect { TODO() }
                    reduce(ProfileEvent.Loaded)
                }
            }

            ProfileIntent.DeleteProfile -> {
                analytics.logButton(ButtonNames.DeleteProfile)

                reduce(ProfileEvent.ShowConfirmPassword)
//                viewModelScope.launch(Dispatchers.IO) {
//                    deleteProfileUseCase()
//                        //todo requires reautheficate (email and password)
//                        // show popup window and ask to inter password
//                        .onStart { reduce(ProfileEvent.Loading) }
//                        .collect { result ->
//                            when (result) {
//                                is UseCaseResult.Error -> reduce(
//                                    ProfileEvent.Error(
//                                        result.error.message ?: "UnknownError"
//                                    )
//                                )
//
//                                is UseCaseResult.Success -> launch(Dispatchers.Main) {
//                                    appNavigator.popAllAndPush(AuthScreen)
//                                }
//                            }
//                            reduce(ProfileEvent.Loaded)
//                        }
//                }
            }

            ProfileIntent.ChangePassword -> {
                analytics.logButton(ButtonNames.ChangePassword)

                viewModelScope.launch(Dispatchers.IO) {
                    changePasswordUseCase(
                        ChangePasswordParams(
                            currentState.oldPasswordValue,
                            currentState.newPasswordValue,
                        )
                    )
                        .onStart { reduce(ProfileEvent.Loading) }
                        .collect { TODO() }
                    reduce(ProfileEvent.Loaded)
                }
            }

            is ProfileIntent.OldPasswordChanged -> {
                reduce(ProfileEvent.OldPasswordChanged(intent.value))
            }

            is ProfileIntent.NewPasswordChanged -> {
                reduce(ProfileEvent.NewPasswordChanged(intent.value))
            }

            ProfileIntent.GoToMovies -> {
                analytics.logButton(ButtonNames.GoBack)

                appNavigator.push(MoviesScreen)
            }
        }
    }
}
