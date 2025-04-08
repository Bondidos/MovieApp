package com.bondidos.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.feature_movies.presentation.testScreen.TestScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                TestScreen()
            }
        }
    }
}

@Composable
@Preview
fun AppPReview() {
    MovieAppTheme {
        TestScreen()
    }
}

