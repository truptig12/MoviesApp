package com.truptig.movie_data.repository

import androidx.paging.PagingSource
import com.truptig.movie_data.local.MovieDao
import com.truptig.movie_data.local.entity.MovieEntity
import com.truptig.movie_data.remote.datasource.MovieRemoteDataSource
import com.truptig.movie_data.remote.dto.MovieDto
import com.truptig.movie_data.remote.dto.SearchResultsDto
import com.truptig.movie_domain.model.Movie
import junit.framework.Assert.assertTrue
import junit.framework.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviePagingSourceTest {

    @Mock
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Mock
    lateinit var movieDao : MovieDao

    private lateinit var moviePagingSource: MoviePagingSource

    @Before
    fun setUp() {
        // Common setup can go here
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns Page when remote data source fetch is successful`() = runTest {
        val query = "love"
        moviePagingSource = MoviePagingSource(remoteDataSource,movieDao, query)
        // Simulate a successful fetch from the remote data source
        val searchResultWithResults = SearchResultsDto<List<MovieDto>>().apply {
            Search = listOf(
                MovieDto("Title 1", "Title 1", "Type 1", "2001","imdb"),
                MovieDto("Title 2", "Title 2", "Type 2", "2001","imdb")
            )
            Response = "200"
            totalResults = "2"
        }
        `when`(remoteDataSource.getMovies(query, 1)).thenReturn(searchResultWithResults)
        val listOfMovies = listOf(
            MovieEntity("Title 1", "Title 1", "Type 1", "2001","imdb"),
            MovieEntity("Title 2", "Title 2", "Type 2", "2001","imdb")
        )
        `when`(movieDao.getAllMovies()).thenReturn(listOfMovies)
        val loadResult = moviePagingSource.load(PagingSource.LoadParams.Refresh<Int>(null, 2, false))

        when (loadResult) {
            is PagingSource.LoadResult.Page -> {
                assertTrue(loadResult is PagingSource.LoadResult.Page)
            }
            else -> fail("Expected PagingSource.LoadResult.Page but got ${loadResult::class.simpleName}")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns Error when remote data source fetch fails`() = runTest {
        val query = "abcdadadddd"
        moviePagingSource = MoviePagingSource(remoteDataSource,movieDao, query)
        val searchResultWithResults = SearchResultsDto<List<MovieDto>>().apply {
            Search = listOf()
            Response = "404"
            totalResults = "0"
        }
        `when`(remoteDataSource.getMovies(query, 1)).thenReturn(searchResultWithResults)

        val loadResult = moviePagingSource.load(PagingSource.LoadParams.Refresh<Int>(null, 2, false))

        assertTrue(loadResult is PagingSource.LoadResult.Error)
    }
}
