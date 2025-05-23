package com.bondidos.auth.presentation.profile.model

import androidx.lifecycle.viewModelScope
import com.bondidos.analytics.AppAnalytics
import com.bondidos.analytics.parameters.ButtonNames
import com.bondidos.analytics.parameters.ScreenNames
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.domain.usecase.ChangePasswordUseCase
import com.bondidos.auth.domain.usecase.DeleteProfileUseCase
import com.bondidos.auth.domain.usecase.GetCurrentUser
import com.bondidos.auth.domain.usecase.LoginUseCase
import com.bondidos.auth.domain.usecase.ResetPasswordUseCase
import com.bondidos.auth.domain.usecase.SingOutUseCase
import com.bondidos.auth.domain.usecase.SingUpWithCredentials
import com.bondidos.auth.domain.usecase.model.ChangePasswordParams
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileEvent
import com.bondidos.auth.presentation.profile.intent.ProfileIntent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.base.UseCaseError
import com.bondidos.base.UseCaseResult
import com.bondidos.navigation_api.AppNavigator
import com.bondidos.navigation_api.AuthScreen
import com.bondidos.navigation_api.MoviesScreen
import com.bondidos.ui.base_mvi.BaseViewModel
import com.bondidos.ui.base_mvi.Intention
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val loginUseCase: LoginUseCase,
    private val singUpWithCredentials: SingUpWithCredentials,
    private val singOutUseCase: SingOutUseCase,
    reducer: ProfileReducer,
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
                                reduce(ProfileEvent.UserData(it, currentUser.data.signInMethod))
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

                handleResetPassword()
            }

            ProfileIntent.DeleteProfile -> {
                analytics.logButton(ButtonNames.DeleteProfile)

                reduce(ProfileEvent.ShowConfirmProfileDelete)
            }

            ProfileIntent.DeleteEmailProfileConfirm -> {
                analytics.logButton(ButtonNames.DeleteProfileConfirm)

                handleDeleteEmailProfileConfirmIntent()
            }

            is ProfileIntent.DeleteGoogleProfileConfirm -> {
                analytics.logButton(ButtonNames.DeleteProfileConfirm)

                handleDeleteGgoogleProfileConfirmIntent(intent)
            }

            ProfileIntent.ChangePassword -> {
                analytics.logButton(ButtonNames.ChangePassword)

                reduce(ProfileEvent.ShowChangePasswordConfirm)
            }

            ProfileIntent.ChangePasswordConfirm -> {
                analytics.logButton(ButtonNames.ChangePasswordConfirm)

                handleChangePassword()
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

    private fun handleChangePassword() {
        viewModelScope.launch(Dispatchers.IO) {
            changePasswordUseCase(
                ChangePasswordParams(
                    currentState.oldPasswordValue,
                    currentState.newPasswordValue,
                )
            )
                .onStart { reduce(ProfileEvent.Loading) }
                .collect { changePasswordResult ->
                    when (changePasswordResult) {
                        is UseCaseResult.Error -> reduce(
                            if (changePasswordResult.error.errorCode == UseCaseError.FIREBASE_AUTH_INVALID_CREDENTIALS)
                                ProfileEvent.InvalidPassword(
                                    changePasswordResult.error.message
                                        ?: "Unknown Error"
                                )
                            else ProfileEvent.Error(
                                changePasswordResult.error.message ?: "Unknown Error"
                            )
                        )

                        is UseCaseResult.Success<*> -> {
                            reduce(ProfileEvent.PasswordChangedSuccessfully)
                            delay(2000)
                            singOutUseCase().collect { result ->
                                when (result) {
                                    is UseCaseResult.Error -> reduce(
                                        ProfileEvent.Error(
                                            result.error.message ?: "Unknown Error"
                                        )
                                    )

                                    is UseCaseResult.Success<*> ->
                                        viewModelScope.launch(Dispatchers.Main) {
                                            appNavigator.popAllAndPush(AuthScreen)
                                        }
                                }
                            }
                        }
                    }
                }
            reduce(ProfileEvent.Loaded)
        }
    }

    private fun handleResetPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            resetPasswordUseCase()
                .onStart { reduce(ProfileEvent.Loading) }
                .collect { resetPasswordResult ->
                    when (resetPasswordResult) {
                        is UseCaseResult.Error -> reduce(ProfileEvent.ResetPasswordFailure)
                        is UseCaseResult.Success<*> -> reduce(ProfileEvent.ResetPasswordSuccess)
                    }
                }
            reduce(ProfileEvent.Loaded)
        }
    }

    private fun handleDeleteGgoogleProfileConfirmIntent(intent: ProfileIntent.DeleteGoogleProfileConfirm) {
        viewModelScope.launch(Dispatchers.IO) {
            singUpWithCredentials(intent.credentials)
                .onStart { reduce(ProfileEvent.Loading) }
                .collect { authUserResult ->
                    when (authUserResult) {
                        is UseCaseResult.Error -> reduce(
                            ProfileEvent.Error(
                                authUserResult.error.message ?: "UnknownError"
                            )
                        )

                        is UseCaseResult.Success<*> -> handleDeleteProfile()
                    }
                    reduce(ProfileEvent.Loaded)
                }
        }
    }

    private fun handleDeleteEmailProfileConfirmIntent() {
        if (currentState.signInMethod == AuthUser.SignInMethod.Email) {
            viewModelScope.launch(Dispatchers.IO) {
                loginUseCase.invoke(currentState.email to currentState.oldPasswordValue)
                    .onStart { reduce(ProfileEvent.Loading) }
                    .collect { authUserUseCaseResult ->
                        when (authUserUseCaseResult) {
                            is UseCaseResult.Error -> reduce(
                                if (authUserUseCaseResult.error.errorCode == UseCaseError.FIREBASE_AUTH_INVALID_CREDENTIALS)
                                    ProfileEvent.InvalidPassword(
                                        authUserUseCaseResult.error.message
                                            ?: "Unknown Error"
                                    )
                                else ProfileEvent.Error(
                                    authUserUseCaseResult.error.message ?: "Unknown Error"
                                )
                            )

                            is UseCaseResult.Success<*> -> handleDeleteProfile()
                        }
                    }
                reduce(ProfileEvent.Loaded)
            }
        }
    }

    private suspend fun handleDeleteProfile() = deleteProfileUseCase.invoke()
        .collect { profileDeleteResult ->
            when (profileDeleteResult) {
                is UseCaseResult.Error -> ProfileEvent.Error(
                    profileDeleteResult.error.message
                        ?: "Unknown Error"
                )

                is UseCaseResult.Success -> viewModelScope.launch(
                    Dispatchers.Main
                ) {
                    appNavigator.popAllAndPush(AuthScreen)
                }
            }
        }
}
