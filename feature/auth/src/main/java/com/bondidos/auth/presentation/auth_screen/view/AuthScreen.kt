package com.bondidos.auth.presentation.auth_screen.view

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bondidos.auth.presentation.auth_screen.intent.AuthEffect
import com.bondidos.auth.presentation.auth_screen.intent.AuthIntent
import com.bondidos.auth.auth_screen.intent.AuthState
import com.bondidos.auth.presentation.SingWithGoogle
import com.bondidos.auth.presentation.auth_screen.model.AuthViewModel
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.ui.composables.AppInputTextField
import com.bondidos.ui.composables.MoviesAppbar
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppScreen
import com.bondidos.utils.ValidationResult

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(viewModel, snackBarHostState) {
        viewModel.effect.collect { action ->
            when (action) {
                is AuthEffect.ValidationError -> {
                    snackBarHostState.showSnackbar(
                        createValidationMessage(context, action.validationResult)
                    )
                }

                is AuthEffect.AuthError -> snackBarHostState.showSnackbar(
                    action.message
                )
            }
        }
    }

    AppScreen(isLoading = state.value.isLoading) {
        AuthScreenContent(
            viewModel = viewModel,
            state = state,
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
fun AuthScreenContent(
    viewModel: AuthViewModel,
    state: State<AuthState>,
    snackBarHostState: SnackbarHostState
) {
    val screenScrollState = rememberScrollState()

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
                    .verticalScroll(screenScrollState)
                    .padding(padding)
                    .padding(all = 25.dp)
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
                Spacer(Modifier.height(80.dp))
                Spacer(modifier = Modifier.heightIn(min = 80.dp))
                SingWithGoogle(
                    onClick = { response -> viewModel.emitIntent(AuthIntent.LoginWithGoogle(response)) },
                )
                Spacer(Modifier.height(48.dp))
            }
        },
    )
}

fun createValidationMessage(context: Context, validationResult: List<ValidationResult?>): String =
    validationResult.mapNotNull {
        it?.let {
            context.getString(it.toStringResId())
        }
    }
        .joinToString(separator = "\n")

fun ValidationResult.toStringResId(): Int {
    return when (this) {
        ValidationResult.EmailValidationResult.EmailIsBlank -> R.string.validation_email_empty
        ValidationResult.EmailValidationResult.EmailTooLong -> R.string.validation_email_too_long
        ValidationResult.EmailValidationResult.EmailWrongFormat -> R.string.validation_email_format
        ValidationResult.EmailValidationResult.EmailWrongDomain -> R.string.validation_email_domain
        ValidationResult.PasswordValidationResult.PasswordIsBlank -> R.string.validation_password_empty
        ValidationResult.PasswordValidationResult.PasswordTooLong -> R.string.validation_password_max
        ValidationResult.PasswordValidationResult.PasswordTooShort -> R.string.validation_password_min
        ValidationResult.PasswordValidationResult.PasswordWrongRequirements -> R.string.validation_password_requirements
        else -> throw IllegalArgumentException()
    }
}