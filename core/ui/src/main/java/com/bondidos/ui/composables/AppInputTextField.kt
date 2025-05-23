package com.bondidos.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bondidos.ui.theme.colors.AppThemeColor
import com.bondidos.ui.R


@Composable
fun AppInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    labelTextRes: Int,
    leadingIconResId: Int,
    isError: Boolean = false,
    keyBoardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Column {
        Text(
            stringResource(labelTextRes),
            style = MaterialTheme.typography.labelMedium,
            color = if (isError)
                AppThemeColor.ATTENTION_TEXT.color() else AppThemeColor.ADDITIONAL_TEXT.color()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            visualTransformation = visualTransformation,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = AppThemeColor.MAIN_TEXT.color()),
            singleLine = true,
            keyboardOptions = keyBoardOptions,
            leadingIcon = {
                Icon(
                    painter = painterResource(leadingIconResId),
                    contentDescription = stringResource(R.string.text_filed_icon_description),
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = AppThemeColor.TEXT_FIELD_BACKGROUND.color(),
                focusedContainerColor = AppThemeColor.TEXT_FIELD_BACKGROUND.color(),
                errorTextColor = AppThemeColor.ATTENTION_TEXT.color()
            ),
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isError)
                        AppThemeColor.ATTENTION_TEXT.color()
                    else AppThemeColor.TEXT_INPUT_BORDER.color(),
                    shape = RoundedCornerShape(4.dp)
                ),
        )
    }
}
