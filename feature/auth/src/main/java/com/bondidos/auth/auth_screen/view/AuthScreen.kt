package com.bondidos.auth.auth_screen.view

import android.content.Context
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.auth.auth_screen.intent.AuthEffect
import com.bondidos.auth.auth_screen.intent.AuthIntent
import com.bondidos.auth.auth_screen.model.AuthViewModel
import com.bondidos.core_ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.AppInputTextField
import com.bondidos.core_ui.theme.composables.MoviesAppbar
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.core_ui.theme.composables.clickable.SignWithGoogleButton
import com.bondidos.ui.R
import com.bondidos.utils.ValidationResult

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val screenScrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { action ->
            when (action) {
                is AuthEffect.ValidationError -> {
                    snackBarHostState.showSnackbar(
                        createValidationMessage(context, action.validationResult)
                    )
                }
            }
        }
    }

    Scaffold(
        containerColor = AppThemeColor.APP_BACKGROUND.color(),
        topBar = {
            MoviesAppbar(
                titleRes = R.string.title_profile,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                afterLeadingTitle = R.string.title_sign_up,
                onAfterLeadingClick = { viewModel.emitIntent(AuthIntent.SignIn) },
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                    onValueChange = { viewModel.emitIntent(AuthIntent.EmailChanged(it)) },
                    labelTextRes = R.string.title_email,
                    leadingIconResId = R.drawable.user_icon,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    isError = state.value.isEmailError
                )
                Spacer(Modifier.height(16.dp))
                AppInputTextField(
                    value = state.value.passwordValue,
                    onValueChange = { viewModel.emitIntent(AuthIntent.PasswordChanged(it)) },
                    labelTextRes = R.string.title_password,
                    leadingIconResId = R.drawable.lock_icon,
                    keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = state.value.isPasswordValueError,
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(80.dp))
                AppColoredButton(
                    onClick = { viewModel.emitIntent(AuthIntent.Login) },
                    titleResId = R.string.button_title_login,
                )
                Spacer(modifier = Modifier.weight(1f))
                SignWithGoogleButton(
                    onClick = { viewModel.emitIntent(AuthIntent.LoginWithGoogle) },
                )
                Spacer(Modifier.height(48.dp))
            }
        },
    )
}

fun createValidationMessage(context: Context, validationResult: List<ValidationResult?>): String =
    validationResult.map {
        it?.let {
            context.getString(it.toStringResId())
        }
    }
        .filterNotNull()
        .joinToString(separator = "\n")

fun ValidationResult.toStringResId(): Int {
    return when (this) {
        ValidationResult.EmailIsBlank -> R.string.validation_email_empty
        ValidationResult.EmailTooLong -> R.string.validation_email_too_long
        ValidationResult.EmailWrongFormat -> R.string.validation_email_format
        ValidationResult.EmailWrongDomain -> R.string.validation_email_domain
        ValidationResult.PasswordIsBlank -> R.string.validation_password_empty
        ValidationResult.PasswordTooLong -> R.string.validation_password_max
        ValidationResult.PasswordTooShort -> R.string.validation_password_min
        ValidationResult.PasswordWrongRequirements -> R.string.validation_password_requirements
        else -> throw IllegalArgumentException()
    }
}