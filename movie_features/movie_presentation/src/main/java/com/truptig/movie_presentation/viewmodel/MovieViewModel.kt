package com.truptig.movie_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.use_case.GetMovieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieData: GetMovieData
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<Movie>> get() = _moviesState


    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getMovies()
                }

                else -> {}
            }
        }
    }

    private suspend fun getMovies() {
        getMovieData.execute(Unit)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
            }
    }
}

sealed class HomeEvent {
    object GetHome : HomeEvent()
}
