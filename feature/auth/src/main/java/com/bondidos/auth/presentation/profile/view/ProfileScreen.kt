package com.bondidos.auth.presentation.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.auth.domain.model.AuthUser
import com.bondidos.auth.presentation.authWithGoogle
import com.bondidos.auth.presentation.profile.intent.BottomSheetType
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileIntent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.auth.presentation.profile.model.ProfileViewModel
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppBottomNavBar
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.ui.composables.bottom_sheet.PasswordResetResult
import com.bondidos.ui.composables.bottom_sheet.ProfileChangePasswordBottomSheet
import com.bondidos.ui.composables.bottom_sheet.ProfileDeleteBottomSheet
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.ui.theme.appColors
import com.bondidos.ui.theme.colors.AppThemeColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(BottomSheetType.None) }
    val context = LocalContext.current

    LaunchedEffect(sheetState, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is ProfileEffect.HandleError -> snackBarHostState.showSnackbar(
                    action.message
                )

                ProfileEffect.ShowConfirmPassword -> showBottomSheet = BottomSheetType.DeleteProfile
                ProfileEffect.ShowResetPasswordFailure -> showBottomSheet =
                    BottomSheetType.ResetPasswordFailure

                ProfileEffect.ShowResetPasswordSuccess -> showBottomSheet =
                    BottomSheetType.ResetPasswordSuccess

                ProfileEffect.ChangePassword -> showBottomSheet =
                    BottomSheetType.ChangePaswword

                ProfileEffect.PasswordChanged -> {
                    showBottomSheet = BottomSheetType.None
                    snackBarHostState.showSnackbar(
                        "TEST"
                    )
                }
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        ProfileScreenContent(
            viewModel,
            state,
            snackBarHostState
        )
    }

    when (showBottomSheet) {
        BottomSheetType.DeleteProfile -> ProfileDeleteBottomSheet(
            oldPasswordValue = state.value.oldPasswordValue,
            onValueChanged = { viewModel.emitIntent(ProfileIntent.OldPasswordChanged(it)) },
            onSubmit = {
                if (state.value.signInMethod == AuthUser.SignInMethod.Email)
                    viewModel.emitIntent(ProfileIntent.DeleteEmailProfileConfirm)
                else CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val credentials = authWithGoogle(context)
                        viewModel.emitIntent(ProfileIntent.DeleteGoogleProfileConfirm(credentials))

                    } catch (e: Throwable) {
                        snackBarHostState.showSnackbar(
                            e.message.toString()
                        )
                    }
                }
            },
            onDismiss = { showBottomSheet = BottomSheetType.None },
            validationError = state.value.isPasswordsNotSame,
            sheetState = sheetState,
            isEmailAuth = state.value.signInMethod == AuthUser.SignInMethod.Email,
            isLoading = state.value.isLoading
        )

        BottomSheetType.ResetPasswordFailure -> PasswordResetResult(
            sheetState = sheetState,
            onDismiss = { showBottomSheet = BottomSheetType.None },
            onSubmit = { showBottomSheet = BottomSheetType.None },
            isSuccess = false

        )

        BottomSheetType.ResetPasswordSuccess -> PasswordResetResult(
            sheetState = sheetState,
            onDismiss = { showBottomSheet = BottomSheetType.None },
            onSubmit = { showBottomSheet = BottomSheetType.None },
            isSuccess = true
        )

        BottomSheetType.ChangePaswword -> ProfileChangePasswordBottomSheet(
            oldPasswordValue = state.value.oldPasswordValue,
            newPasswordValue = state.value.newPasswordValue,
            onOldValueChanged = { viewModel.emitIntent(ProfileIntent.OldPasswordChanged(it)) },
            onNewValueChanged = { viewModel.emitIntent(ProfileIntent.NewPasswordChanged(it)) },
            sheetState = sheetState,
            validationError = state.value.isPasswordsNotSame,
            isLoading = state.value.isLoading,
            onSubmit = { viewModel.emitIntent(ProfileIntent.ChangePasswordConfirm) },
            onDismiss = { showBottomSheet = BottomSheetType.None },
        )

        BottomSheetType.None -> Unit
    }
}

@Composable
private fun ProfileScreenContent(
    viewModel: ProfileViewModel,
    state: State<ProfileState>,
    snackBarHostState: SnackbarHostState
) {
    val screenScrollState = rememberScrollState()
    val data = state.value

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_profile,
                titleTextStyle = MaterialTheme.typography.titleLarge,
            )
        },
        bottomBar = {
            AppBottomNavBar(
                onProfileClick = {},
                onMovieClick = { viewModel.emitIntent(ProfileIntent.GoToMovies) },
                currentItem = AppBottomNavBar.PROFILE
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(screenScrollState)
                .padding(padding)
                .padding(all = 25.dp)
        ) {
            Text(
                data.email,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.appColors.mainText,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(Modifier.height(20.dp))
                AppColoredButton(
                    onClick = { viewModel.emitIntent(ProfileIntent.ChangePassword) },
                    color = MaterialTheme.appColors.activeButtonColor,
                    titleResId = R.string.button_title_change
                )
                Spacer(Modifier.height(20.dp))
                AppColoredButton(
                    onClick = { viewModel.emitIntent(ProfileIntent.ResetPassword) },
                    color = MaterialTheme.appColors.activeButtonColor,
                    titleResId = R.string.button_title_reset
                )
                Spacer(Modifier.height(20.dp))
                AppColoredButton(
                    onClick = { viewModel.emitIntent(ProfileIntent.DeleteProfile) },
                    color = MaterialTheme.appColors.activeButtonColor,
                    titleResId = R.string.button_title_delete
                )
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}