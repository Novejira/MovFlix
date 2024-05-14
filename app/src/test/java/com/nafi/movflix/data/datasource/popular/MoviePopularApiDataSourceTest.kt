package com.nafi.movflix.data.datasource.popular

import com.nafi.movflix.data.source.network.model.movie.MoviePopularResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviePopularApiDataSourceTest {
    @MockK
    lateinit var service: MovFlixApiService

    private lateinit var dataSource: MoviePopularDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MoviePopularApiDataSource(service)
    }

    @Test
    fun getMovieList() {
        runTest {
            val mockResponse = mockk<MoviePopularResponse>(relaxed = true)
            coEvery { service.getPopularMovies() } returns mockResponse
            val actualResult = dataSource.getMovieList()
            assertEquals(mockResponse, actualResult)
        }
    }
}
