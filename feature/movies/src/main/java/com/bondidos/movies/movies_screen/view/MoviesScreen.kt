package com.bondidos.movies.movies_screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bondidos.movies.movies_screen.model.MoviesScreenViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "MovieScreen")
        ElevatedButton(
            onClick = {
                viewModel.navigateToMovies()
            },
        ) {
            Text("Navigate to AuthFeature")
        }
    }
}
