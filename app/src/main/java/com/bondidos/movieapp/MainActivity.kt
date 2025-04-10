package com.bondidos.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bondidos.core_navigation_impl.AppNavigationGraph
import com.bondidos.core_navigation_impl.di.NavControllerHolder
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.feature_auth.auth_screen.AuthScreen
import com.bondidos.feature_movies.presentation.movies_screen.MoviesScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navControllerHolder: NavControllerHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MovieAppTheme {
                AppNavigationGraph(
                    authFeature = { AuthScreen() },
                    moviesFeature = { MoviesScreen() },
                    navControllerHolder = navControllerHolder,
                )
            }
        }
    }
}
