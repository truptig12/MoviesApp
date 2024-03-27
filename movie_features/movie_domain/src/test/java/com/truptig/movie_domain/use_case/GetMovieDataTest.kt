package com.truptig.movie_domain.use_case

import androidx.paging.PagingData
import com.truptig.movie_domain.model.Movie
import com.truptig.movie_domain.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieDataTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieData: GetMovieData

    @Before
    fun setUp() {
        getMovieData = GetMovieData(movieRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `execute returns correct movie data`() = runBlockingTest {
        val movieInput = "abc"
        val expectedMovies: Flow<PagingData<Movie>> = flowOf(PagingData.empty())
        `when`(movieRepository.getMovie(movieInput)).thenReturn(expectedMovies)

        val result = getMovieData.execute(movieInput)

        verify(movieRepository).getMovie(movieInput)
        assert(result == expectedMovies)
    }
}
