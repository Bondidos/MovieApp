package com.bondidos.core_ui.theme.composables.clickable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.core_ui.theme.colors.AppThemeColor
import com.bondidos.ui.R

@Composable
fun SignWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .size(44.dp, 44.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = AppThemeColor.BUTTON_ACTIVE.color(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.logo_google_plus),
                contentDescription = "",
                tint = AppThemeColor.ON_BUTTON_TEXT.color()
            )
        }
    }
}

@Preview
@Composable
fun AppBottomNavBarPreview() {
    MovieAppTheme {
        SignWithGoogleButton(
            onClick = {},
        )
    }
}