package com.bondidos.core_ui.theme.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bondidos.core_ui.R
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.core_ui.theme.colors.AppThemeColor

@Composable
fun MoviesAppbar(
    modifier: Modifier = Modifier,
    titleRes: Int,
    titleTextStyle: TextStyle,
    afterLeadingTitle: Int? = null,
    onAfterLeadingClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier.height(88.dp),
        shadowElevation = 10.dp,
        color = AppThemeColor.APP_BACKGROUND.color(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 13.dp, start = 18.dp, end = 18.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(titleRes),
                color = AppThemeColor.MAIN_TEXT.color(),
                style = titleTextStyle
            )
            if (afterLeadingTitle != null) {
                Spacer(
                    modifier = Modifier.weight(
                        fill = true,
                        weight = 1f
                    )
                )
                Text(
                    modifier = Modifier.clickable { onAfterLeadingClick() },
                    text = stringResource(afterLeadingTitle),
                    color = AppThemeColor.ATTENTION_TEXT.color(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesAppbarPreview() {
    MovieAppTheme {
        MoviesAppbar(
            titleRes = R.string.title_profile,
            titleTextStyle = MaterialTheme.typography.titleLarge,
            afterLeadingTitle = R.string.title_sign_up,
            onAfterLeadingClick = { print("clicked") },
        )
    }
}