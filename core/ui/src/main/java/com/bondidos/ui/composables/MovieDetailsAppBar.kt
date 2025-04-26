package com.bondidos.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.bondidos.ui.R
import com.bondidos.ui.theme.MovieAppTheme

@Composable
fun MovieDetailsAppBar(
    modifier: Modifier = Modifier,
    onBackArrowClick: () -> Unit,
    onPlayClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.back_arrow),
            contentDescription = null,
            modifier = Modifier.clickable { onBackArrowClick() }
        )
        Image(
            painter = painterResource(R.drawable.play_button),
            contentDescription = null,
            modifier = Modifier.clickable { onPlayClick() }
        )
        Image(
            painter = painterResource(R.drawable.share_arrow),
            contentDescription = null,
            modifier = Modifier.clickable { onShareClick() }
        )
    }
}

@Preview(device = Devices.PIXEL)
@Composable
fun MovieDetailsAppBarPreview() {
    MovieAppTheme {
        MovieDetailsAppBar(Modifier, {}, {}, {})
    }
}