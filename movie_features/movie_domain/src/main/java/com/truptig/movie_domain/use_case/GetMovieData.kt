package com.truptig.movie_domain.use_case

import androidx.paging.PagingData
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieData @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<String, Flow<PagingData<Movie>>> {
    override suspend fun execute(input: String): Flow<PagingData<Movie>> {
        return repository.getMovie(input)
    }
}