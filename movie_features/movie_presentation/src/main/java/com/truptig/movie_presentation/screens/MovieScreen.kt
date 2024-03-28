package com.truptig.movie_presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.truptig.core.utils.Resource
import com.truptig.movie_presentation.viewmodel.MovieViewModel


@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    navController: NavHostController
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(7))
            .background(Color.White)
            .clickable { }
    ) {

        viewModel.movieDetailsState.collectAsState().value.let { movie ->

            when (val state = movie) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    state.data?.let { movieDetails ->
                        Movie(movieDetails, navController)
                    } ?: Text("No details available")
                }

                is Resource.Error -> {
                    Text("Failed to load movie details: ${state.message}")
                }
            }
        }
    }
}


