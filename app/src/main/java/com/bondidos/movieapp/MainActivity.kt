package com.bondidos.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bondidos.navigation_impl.AppNavigationGraph
import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.core_ui.theme.MovieAppTheme
import com.bondidos.auth.auth_screen.view.AuthScreen
import com.bondidos.movies.movies_screen.MoviesScreen
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
