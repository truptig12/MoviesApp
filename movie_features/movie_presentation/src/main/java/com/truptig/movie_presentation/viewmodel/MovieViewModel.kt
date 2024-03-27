package com.truptig.movie_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.truptig.core.utils.MoviesConstants.Companion.DEFAULT
import com.truptig.core.utils.Resource
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.model.MovieDetails
import com.truptig.movie_domain.use_case.GetMovieData
import com.truptig.movie_domain.use_case.GetMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieData: GetMovieData,
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<Movie>> get() = _moviesState

    private val _movieDetailsState = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading())
    val movieDetailsState: StateFlow<Resource<MovieDetails>> = _movieDetailsState.asStateFlow()

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getMovies(DEFAULT)
                }

                is HomeEvent.Search -> {
                    getMovies(event.input)
                }

                is HomeEvent.Details -> {
                    loadMovieDetails(event.title)
                }

                else -> {}
            }
        }
    }

    private suspend fun getMovies(input: String) {
        getMovieData.execute(input)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }

    internal fun loadMovieDetails(word: String) {
        viewModelScope.launch {
            getMovieDetails(word).collect { resource ->
                _movieDetailsState.value = resource
            }
        }
    }
}
sealed class HomeEvent {
    object GetHome : HomeEvent()
    class Search(val input: String) : HomeEvent()

    class Details(val title:String):HomeEvent()
}
