package com.bondidos.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bondidos.auth.presentation.auth_screen.view.AuthScreen
import com.bondidos.auth.presentation.sing_up_screen.view.SingUpScreen
import com.bondidos.movies.presentation.cast_and_crew.view.CastAndCrewScreen
import com.bondidos.movies.presentation.movie_details_screen.view.MovieDetailsScreen
import com.bondidos.movies.presentation.movies_screen.view.MoviesScreen
import com.bondidos.navigation_impl.AppNavigationGraph
import com.bondidos.navigation_impl.di.NavControllerHolder
import com.bondidos.splash.SplashScreen
import com.bondidos.ui.theme.MovieAppTheme
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
                    authScreen = { AuthScreen() },
                    singUpScreen = { SingUpScreen() },
                    moviesScreen = { MoviesScreen() },
                    splashScreen = { SplashScreen() },
                    movieDetailsScreen = { MovieDetailsScreen() },
                    navControllerHolder = navControllerHolder,
                    castAndCrewScreen = { CastAndCrewScreen() },
                )
            }
        }
    }
}
