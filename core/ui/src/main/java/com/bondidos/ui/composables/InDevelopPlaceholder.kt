package com.bondidos.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bondidos.ui.R
import com.bondidos.ui.theme.appColors

@Composable
fun InDevelopPlaceHolder(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(400.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.title_in_develop),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.appColors.mainText,
        )
    }
}