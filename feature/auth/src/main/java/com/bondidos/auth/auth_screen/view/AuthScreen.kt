package com.bondidos.auth.auth_screen.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.auth.auth_screen.intent.AuthEvent
import com.bondidos.auth.auth_screen.model.AuthViewModel
import com.bondidos.core_ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.AppInputTextField
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.core_ui.theme.composables.clickable.SignWithGoogleButton
import com.bondidos.ui.R

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    // TODo MainViewModel for navigation?
    val state = viewModel.state.collectAsStateWithLifecycle()
//    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val screenScrollState = rememberScrollState()

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_profile,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                afterLeadingTitle = R.string.title_sign_up,
                onAfterLeadingClick = { viewModel.sendEvent(AuthEvent.SingUp) },
            )
        },
        content = { padding ->
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(all = 25.dp)
                    .scrollable(screenScrollState, orientation = Orientation.Vertical)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                AppInputTextField(
                    value = state.value.emailValue,
                    onValueChange = { value ->
                        viewModel.sendEvent(
                            AuthEvent.EmailValueChanged(
                                value
                            )
                        )
                    },
                    labelTextRes = R.string.title_email,
                    leadingIconResId = R.drawable.user_icon,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isError = state.value.isEmailError
                )
                Spacer(Modifier.height(16.dp))
                AppInputTextField(
                    value = state.value.passwordValue,
                    onValueChange = { value ->
                        viewModel.sendEvent(
                            AuthEvent.PasswordValueChanged(
                                value
                            )
                        )
                    },
                    labelTextRes = R.string.title_password,
                    leadingIconResId = R.drawable.lock_icon,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = state.value.isPasswordValueError,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(80.dp))
                AppColoredButton(
                    onClick = { viewModel.sendEvent(AuthEvent.Login) },
                    titleResId = R.string.button_title_login,
                )
                Spacer(modifier = Modifier.weight(1f))
                SignWithGoogleButton(
                    onClick = { viewModel.sendEvent(AuthEvent.LoginWithGoogle) },
                )
                Spacer(Modifier.height(48.dp))
            }
        },
    )
}
