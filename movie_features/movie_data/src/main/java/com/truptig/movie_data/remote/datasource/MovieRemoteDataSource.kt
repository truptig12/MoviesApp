package com.truptig.movie_data.remote.datasource

import com.truptig.movie_data.remote.dto.MovieDto
import com.truptig.movie_data.remote.dto.SearchResultsDto

interface MovieRemoteDataSource {
    suspend fun getMovies(
        apiKey: String,
        pageNumber: Int
    ): SearchResultsDto<List<MovieDto>>

}
