package com.bondidos.core_ui.theme.composables.clickable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bondidos.core_ui.theme.colors.AppThemeColor

@Composable
fun AppColoredButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = AppThemeColor.ACTIVE_BUTTON.color(),
    titleResId: Int,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(elevation = 10.dp)
            .clickable { onClick() },
        color = color,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                stringResource(titleResId),
                color = AppThemeColor.ON_BUTTON_TEXT.color(),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}