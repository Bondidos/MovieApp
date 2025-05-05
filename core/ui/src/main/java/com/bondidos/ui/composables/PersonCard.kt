package com.bondidos.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bondidos.ui.theme.appColors

@Composable
fun PersonCard(
    imageUrl: String,
    personName: String,
    role: String
) {
    val additionalContentWidth = LocalConfiguration.current.screenWidthDp / 3

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageWithName(imageUrl, personName)
        Row(
            modifier = Modifier.width(additionalContentWidth.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HorizontalMoreIndicator()
            Text(
                role,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.appColors.disabledText,
            )
        }
    }
}

@Composable
private fun ImageWithName(
    imageUrl: String,
    personName: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        NetworkImage(
            url = imageUrl,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.padding(start = 12.dp))
        Text(
            personName,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.appColors.mainText,
        )
    }
}

@Composable
private fun HorizontalMoreIndicator() {
    Row {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.appColors.disabledText)
                    .padding(end = 4.dp)
            )
        }
    }
}
