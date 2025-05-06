package com.bondidos.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.theme.MovieAppTheme
import com.bondidos.ui.theme.colors.AppThemeColor

@Composable
fun MoviesAppbar(
    modifier: Modifier = Modifier,
    titleRes: Int,
    titleTextStyle: TextStyle,
    afterLeadingTitle: Int? = null,
    onAfterLeadingClick: () -> Unit = {},
    leadingIconRes: Int? = null,
    onLeadingClick: () -> Unit = {},
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
            if (leadingIconRes != null) {
                Image(
                    painter = painterResource(leadingIconRes),
                    contentDescription = null,
                    modifier = Modifier.clickable { onLeadingClick() }
                )
                Spacer(
                    modifier = Modifier.weight(
                        fill = true,
                        weight = 1f
                    )
                )
            }
            Text(
                text = stringResource(titleRes),
                color = AppThemeColor.MAIN_TEXT.color(),
                style = titleTextStyle
            )
            if (afterLeadingTitle != null || leadingIconRes != null)
                Spacer(
                    modifier = Modifier.weight(
                        fill = true,
                        weight = 1f
                    )
                )
            if (afterLeadingTitle != null)
                Text(
                    modifier = Modifier.clickable { onAfterLeadingClick() },
                    text = stringResource(afterLeadingTitle),
                    color = AppThemeColor.ATTENTION_TEXT.color(),
                    style = MaterialTheme.typography.titleMedium,
                )

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