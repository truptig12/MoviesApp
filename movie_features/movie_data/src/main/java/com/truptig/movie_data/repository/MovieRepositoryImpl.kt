package com.truptig.movie_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.truptig.core.utils.Resource
import com.truptig.movie_data.local.MovieDao
import com.truptig.movie_data.remote.MovieApi
import com.truptig.movie_data.remote.datasource.MovieRemoteDataSourceImpl
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.model.MovieDetails
import com.truptig.movie_domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {

    override fun getMovie(input : String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(MovieRemoteDataSourceImpl(api),dao, input)
            }
        ).flow
    }

    override fun getMovieDetails(input: String): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading())

        try {
            val movieDetailResponse = api.getMovieDetails(input, apiKey = "b854af04")
            val movieDetails = movieDetailResponse.toMovieDetailsEntity()
            dao.saveMovieDetails(movieDetails)

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops! Something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach servers, Check your internet connectivity"
                )
            )
        }
        val newMovieInfo = dao.getMovieDetailsById(input)
        if (newMovieInfo != null) {
            emit(Resource.Success(newMovieInfo.toMovieDetails()))
        } else {
            emit(Resource.Error("No movie details found for ID: $input"))
        }

    }
}