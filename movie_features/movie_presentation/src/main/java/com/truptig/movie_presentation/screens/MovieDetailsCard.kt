package com.truptig.movie_presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_presentation.components.ProgressiveGlowingImage


@Composable
fun MovieDetailsCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onExpandMovieDetails: (Movie) -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(7))
            .background(Color.White)
            .clickable { onExpandMovieDetails(movie) }
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Row(
                Modifier
                    .height(40.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(10.dp))

                Text(
                    text = movie.Title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                        .weight(1f),
                )
                Box(Modifier.padding(horizontal = 5.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { onClose() }
                            .padding(5.dp)
                    )
                }
            }
            val backdropUrl = movie.Poster
            val thumbBackdropUrl = movie.Poster
            if (backdropUrl != null && thumbBackdropUrl != null) {
                ProgressiveGlowingImage(backdropUrl, thumbBackdropUrl, glow = true)
            }
            Text(
                text = movie.Title,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 22.dp, end = 12.dp)
            )
            Row(Modifier.padding(horizontal = 12.dp)) {
                Rating(voteAverage = movie.Type.toString())
                Spacer(Modifier.width(7.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 3.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = null,
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { }
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Rating(voteAverage: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.onPrimary)
            .width(80.dp)
    ) {
        Row(
            Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = voteAverage,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
