package com.nafi.movflix.data.datasource.nowplaying

import com.nafi.movflix.data.source.network.model.movie.MovieNowPlayingResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieNowPlayingApiDataSourceTest {
    @MockK
    lateinit var service: MovFlixApiService

    private lateinit var dataSource: MovieNowPlayingDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MovieNowPlayingApiDataSource(service)
    }

    @Test
    fun getMovieList() {
        runTest {
            val mockResponse = mockk<MovieNowPlayingResponse>(relaxed = true)
            val mockMinDate = "2024-04-01"
            val mockMaxDate = "2024-05-15"
            coEvery { service.getNowPlayingMovies(minDate = mockMinDate, maxDate = mockMaxDate) } returns mockResponse
            val actualResult = dataSource.getMovieList()
            assertEquals(mockResponse, actualResult)
        }
    }
}
