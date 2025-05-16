package com.bondidos.ui.composables.bottom_sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.composables.clickable.AppColoredButton
import com.bondidos.ui.theme.appColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetResult(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    isSuccess: Boolean
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(if (isSuccess) R.string.title_password_reset_success else R.string.title_password_reset_failure),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.appColors.mainText,
            )
            Spacer(Modifier.height(20.dp))

            AppColoredButton(
                onClick = onSubmit,
                color = MaterialTheme.appColors.activeButtonColor,
                titleResId = R.string.button_title_confirm
            )
        }
    }
}