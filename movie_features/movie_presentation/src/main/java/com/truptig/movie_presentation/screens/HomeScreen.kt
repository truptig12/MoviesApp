package com.truptig.movie_presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.truptig.core.ui.Dimens.bottomPadding
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_presentation.components.ErrorMessage
import com.truptig.movie_presentation.components.ItemMovie
import com.truptig.movie_presentation.components.LoadingNextPageItem
import com.truptig.movie_presentation.components.PageLoader
import com.truptig.movie_presentation.components.SearchField
import com.truptig.movie_presentation.viewmodel.HomeEvent
import com.truptig.movie_presentation.viewmodel.MovieViewModel


@Composable
fun HomeScreen(
    viewModel: MovieViewModel,
    navController: NavHostController
) {

   // val viewModel: MovieViewModel = hiltViewModel()
    val moviePagingItems: LazyPagingItems<Movie> = viewModel.moviesState.collectAsLazyPagingItems()
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Movies App",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .weight(1.0f),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it)) {
            SearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.onEvent(HomeEvent.Search(searchText)) },
                hint ="Search",
                onClick = {
                    searchText = ""
                    viewModel.onEvent(HomeEvent.Search(""))
                }
            )
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = bottomPadding)
            ) {

                items(moviePagingItems.itemCount) { index ->
                    ItemMovie(

                        itemEntity = moviePagingItems[index]!!,
                        onClick = {
                            /*MovieDetailsPopup(
                            movie =  moviePagingItems[index]!!,
                            onExpandMovieDetails = { movie ->
                                onExpandMovieDetails(movie)
                                coroutineScope.launch {
                                    delay(300)
                                    onCloseMovieDetails()
                                }
                            }
                        )*/
                            viewModel.onEvent(HomeEvent.Details(moviePagingItems[index]!!.Title))
                            navController.navigate("details")
                        }
                    )
                }
                moviePagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { PageLoader(modifier = Modifier.fillMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = moviePagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = moviePagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }

           /* LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xfff9f9f9))
            ) {
                item { Spacer(modifier = Modifier.padding(4.dp)) }
                items(moviePagingItems.itemCount) { index ->
                    ItemMovie(
                        itemEntity = moviePagingItems[index]!!,
                        onClick = {
                            viewModel.onEvent(HomeEvent.Details(moviePagingItems[index]!!.Title))
                            navController.navigate("details")

                            //  navController.navigate("details/${moviePagingItems[index]!!.Title}")
                        }
                    )
                }
                moviePagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = moviePagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem(modifier = Modifier) }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = moviePagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.padding(4.dp)) }
            }*/
        }
    }
}