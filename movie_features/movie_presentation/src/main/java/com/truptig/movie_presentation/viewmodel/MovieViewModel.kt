package com.truptig.movie_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.truptig.core.ui.MoviesConstants.Companion.DEFAULT
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.use_case.GetMovieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieData: GetMovieData
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty())
    private val selectedMovieFlow = MutableStateFlow<Movie?>(value = null)
    val moviesState: MutableStateFlow<PagingData<Movie>> get() = _moviesState
    val selectedMovie: StateFlow<Movie?> = selectedMovieFlow

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getMovies(DEFAULT)
                }
                is HomeEvent.Search ->{
                    getMovies(event.input)
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

    fun showMovieDetails(movie: Movie) {
        selectedMovieFlow.value = movie
    }
}

sealed class HomeEvent {
    object GetHome : HomeEvent()
    class Search(val input: String) : HomeEvent()
}
