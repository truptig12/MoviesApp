package com.truptig.movie_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.truptig.movie_data.local.MovieDao
import com.truptig.movie_data.remote.MovieApi
import com.truptig.movie_data.remote.datasource.MovieRemoteDataSourceImpl
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val dao: MovieDao
) : MovieRepository {

    override fun getMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(MovieRemoteDataSourceImpl(api),dao)
            }
        ).flow
    }
}