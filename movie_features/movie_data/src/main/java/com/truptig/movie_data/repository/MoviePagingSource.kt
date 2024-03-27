package com.truptig.movie_data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.truptig.movie_data.local.MovieDao
import com.truptig.movie_data.remote.datasource.MovieRemoteDataSource
import com.truptig.movie_domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val remoteDataSource: MovieRemoteDataSource,
    private val dao: MovieDao,
    private val input: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = remoteDataSource.getMovies(
                input = input,
                pageNumber = currentPage
            )
            Log.d("movies api", movies.Search?.size.toString())
            if (movies.Search?.size != null) {
                dao.deleteMovies()
                Log.d("movies api", movies.Search?.size.toString())
                dao.insertMovies(movies.Search!!.filter { it.Year.toInt() > 2000 }
                    .sortedBy { it.Year }.map { it.toMovieEntity() })
                if (dao.getAllMovies().isNullOrEmpty()) {
                    LoadResult.Error(Exception("Something Went Wrong"))
                } else {
                    val newMovie = dao.getAllMovies().map { it.toMovie() }
                    LoadResult.Page(
                        data = newMovie,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (movies.Search!!.isEmpty()) null else currentPage!! + 1
                    )
                }
            } else {
                LoadResult.Error(Exception("Something Went Wrong"))
            }


        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

}