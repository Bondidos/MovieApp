package com.bondidos.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bondidos.ui.theme.appColors

@Composable
fun MovieCard(
    title: String,
    genre: String,
    certification: String,
    image: String,
    stars: Int,
    duration: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        NetworkImage(
            url = image, modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(18.dp))
        MovieStarRow(stars)
        Spacer(Modifier.height(7.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(lineHeight = 18.sp),
            color = MaterialTheme.appColors.mainText
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$genre $duration | $certification",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.appColors.disabledText
        )
        Spacer(Modifier.height(7.dp))
    }
}
