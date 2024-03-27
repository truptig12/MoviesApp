package com.truptig.movie_domain.use_case

import com.truptig.core.utils.Resource
import com.truptig.movie_domain.model.MovieDetails
import com.truptig.movie_domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieDetails @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(word:String): Flow<Resource<MovieDetails>>{
        if(word.isBlank()){
            return flow {  }
        }
        return repository.getMovieDetails(word)
    }
}