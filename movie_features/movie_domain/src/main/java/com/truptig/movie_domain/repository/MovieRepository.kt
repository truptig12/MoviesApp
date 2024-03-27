package com.truptig.movie_domain.repository


import androidx.paging.PagingData
import com.truptig.movie_domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(input : String): Flow<PagingData<Movie>>

}