package com.truptig.movie_domain.repository


import androidx.paging.PagingData
import com.truptig.core.utils.Resource
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(input : String): Flow<PagingData<Movie>>

    fun getMovieDetails(input:String):Flow<Resource<MovieDetails>>
}