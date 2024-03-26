package com.truptig.movie_data.remote.datasource

import com.truptig.movie_data.remote.MovieApi
import com.truptig.movie_data.remote.dto.MovieDto
import com.truptig.movie_data.remote.dto.SearchResultsDto
import javax.inject.Inject


class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: MovieApi,
) : MovieRemoteDataSource {

    override suspend fun getMovies(
        apiKey: String,
        pageNumber: Int
    ): SearchResultsDto<List<MovieDto>> {
        return api.getAllMovies("love", apiKey = apiKey, page = pageNumber)
    }

}