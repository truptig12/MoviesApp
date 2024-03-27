package com.truptig.movie_presentation.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.truptig.core.ui.Dimens.bottomPadding
import com.truptig.movie_domain.model.Movie


private enum class State {
    PreDisplayed, Displayed, PostDisplayed
}

@Composable
fun MovieDetailsPopup(
    movie: Movie,
    onExpandMovieDetails: (Movie) -> Unit
) {
    var state by remember { mutableStateOf(State.PreDisplayed) }
    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            State.PreDisplayed, State.PostDisplayed -> Color.Transparent
            State.Displayed -> Color.Black
        },
        animationSpec = tween(300),
        finishedListener = {
            //if (state == State.PostDisplayed) onClosed()
        }
    )
    val offset by animateDpAsState(
        targetValue = when (state) {
            State.PreDisplayed, State.PostDisplayed -> 1000.dp
            State.Displayed -> 0.dp
        },
        animationSpec = tween(300)
    )

    LaunchedEffect(Unit) {
        state = State.Displayed
    }

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        Modifier.clickable(interactionSource, null) { state = State.PostDisplayed },
        contentAlignment = Alignment.BottomCenter
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        )
        MovieDetailsCard(
            movie,
            Modifier
                .offset(y = offset)
                .padding(bottom = bottomPadding)
                .clickable(interactionSource, null) {},
            onClose = { state = State.PostDisplayed },
            onExpandMovieDetails
        )
    }
}