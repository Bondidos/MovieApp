package com.bondidos.auth.presentation.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.auth.presentation.profile.intent.ProfileEffect
import com.bondidos.auth.presentation.profile.intent.ProfileIntent
import com.bondidos.auth.presentation.profile.intent.ProfileState
import com.bondidos.auth.presentation.profile.model.ProfileViewModel
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppInputTextField
import com.bondidos.ui.composables.AppScreen
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.ui.theme.appColors
import com.bondidos.ui.theme.colors.AppThemeColor

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is ProfileEffect.HandleError -> snackBarHostState.showSnackbar(
                    action.message
                )
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
                leadingIconRes = R.drawable.back_arrow,
                onLeadingClick = { viewModel.emitIntent(ProfileIntent.GoBack) })
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
                AppInputTextField(
                    value = data.oldPasswordValue,
                    onValueChange = { viewModel.emitIntent(ProfileIntent.OldPasswordChanged(it)) },
                    labelTextRes = R.string.label_old_password,
                    leadingIconResId = R.drawable.lock_icon,
                    isError = data.isPasswordsNotSame,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                )
                Spacer(Modifier.height(20.dp))
                AppInputTextField(
                    value = data.newPasswordValue,
                    onValueChange = { viewModel.emitIntent(ProfileIntent.NewPasswordChanged(it)) },
                    labelTextRes = R.string.label_new_password,
                    leadingIconResId = R.drawable.lock_icon,
                    isError = data.isPasswordsNotSame,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                )
            }

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