package com.truptig.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.truptig.core.ui.AppRoutes
import com.truptig.movie_presentation.screens.HomeScreen
import com.truptig.movie_presentation.screens.MovieScreen
import com.truptig.movie_presentation.viewmodel.MovieViewModel
import com.truptig.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    StartApp()
                }
            }
        }
    }

    @Composable
    private fun StartApp() {
        val viewModel: MovieViewModel = hiltViewModel()
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = AppRoutes.HOME) {
            composable(AppRoutes.HOME) {
                HomeScreen(viewModel, navController)
            }
            composable(AppRoutes.DETAILS) {
                MovieScreen(viewModel, navController)
            }
        }
    }
}
