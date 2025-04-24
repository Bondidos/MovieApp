package com.bondidos.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bondidos.ui.R

@Composable
fun NetworkImage(
    url: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .memoryCacheKey(url)
                .diskCacheKey(url)
                .crossfade(300)
                .placeholder(R.drawable.landscape_placeholder)
                .error(R.drawable.error_placeholder)
                .build()

        ),
        contentScale = contentScale,
        contentDescription = null,
        modifier = modifier,
    )
}