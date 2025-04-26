package com.bondidos.ui.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
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
//            .padding(top = 45.dp),
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