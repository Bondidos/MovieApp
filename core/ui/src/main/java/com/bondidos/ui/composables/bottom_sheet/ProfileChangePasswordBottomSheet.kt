package com.bondidos.ui.composables.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.composables.AppInputTextField
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.ui.theme.appColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileChangePasswordBottomSheet(
    oldPasswordValue: String,
    newPasswordValue: String,
    onOldValueChanged: (String) -> Unit,
    onNewValueChanged: (String) -> Unit,
    sheetState: SheetState,
    validationError: Boolean,
    isLoading: Boolean,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit,
    ) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(R.string.title_password_change),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.appColors.mainText,
            )
            Spacer(Modifier.height(20.dp))

            AppInputTextField(
                value = oldPasswordValue,
                onValueChange = { onOldValueChanged(it) },
                labelTextRes = R.string.label_old_password,
                leadingIconResId = R.drawable.lock_icon,
                isError = validationError,
                keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(20.dp))

            AppInputTextField(
                value = newPasswordValue,
                onValueChange = { onNewValueChanged(it) },
                labelTextRes = R.string.label_new_password,
                leadingIconResId = R.drawable.lock_icon,
                isError = validationError,
                keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(20.dp))

            AppColoredButton(
                onClick = {
                    keyboardController?.hide()
                    if (!isLoading) onSubmit()
                },
                color = if (isLoading) MaterialTheme.appColors.disabledText else MaterialTheme.appColors.activeButtonColor,
                titleResId = R.string.button_title_confirm
            )
        }
    }
}