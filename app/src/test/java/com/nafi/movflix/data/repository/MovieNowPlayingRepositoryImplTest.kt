package com.nafi.movflix.data.repository

import app.cash.turbine.test
import com.nafi.movflix.data.datasource.nowplaying.MovieNowPlayingDataSource
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse
import com.nafi.movflix.data.source.network.model.movie.MovieNowPlayingResponse
import com.nafi.movflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieNowPlayingRepositoryImplTest {
    @MockK
    lateinit var dataSource: MovieNowPlayingDataSource

    private lateinit var repository: MovieNowPlayingRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MovieNowPlayingRepositoryImpl(dataSource)
    }

    @Test
    fun getMoviesNowPlaying_loading() {
        val movie1 =
            MovieListResponse(
                backdropPath = "sadasdasda",
                id = 1,
                title = "asdsdadasd",
                desc = "asdsdasddsa",
                popularity = 7.2,
                posterPath = "asdsdasdas",
                releaseDate = "asdasdasda",
                voteAverage = 1.0,
                voteCount = 1,
            )
        val movie2 =
            MovieListResponse(
                backdropPath = "sadasdasda",
                id = 1,
                title = "asdsdadasd",
                desc = "asdsdasddsa",
                popularity = 7.2,
                posterPath = "asdsdasdas",
                releaseDate = "asdasdasda",
                voteAverage = 1.0,
                voteCount = 1,
            )
        val mockListMovie = listOf(movie1, movie2)
        val mockResponse = mockk<MovieNowPlayingResponse>()
        coEvery { mockResponse.data } returns mockListMovie
        runTest {
            coEvery { dataSource.getMovieList() } returns mockResponse
            repository.getMoviesNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(201)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getMovieList() }
            }
        }
    }

    @Test
    fun getMoviesNowPlaying_success() {
        val movie1 =
            MovieListResponse(
                backdropPath = "sadasdasda",
                id = 1,
                title = "asdsdadasd",
                desc = "asdsdasddsa",
                popularity = 7.2,
                posterPath = "asdsdasdas",
                releaseDate = "asdasdasda",
                voteAverage = 1.0,
                voteCount = 1,
            )
        val movie2 =
            MovieListResponse(
                backdropPath = "sadasdasda",
                id = 1,
                title = "asdsdadasd",
                desc = "asdsdasddsa",
                popularity = 7.2,
                posterPath = "asdsdasdas",
                releaseDate = "asdasdasda",
                voteAverage = 1.0,
                voteCount = 1,
            )
        val mockListMovie = listOf(movie1, movie2)
        val mockResponse = mockk<MovieNowPlayingResponse>()
        coEvery { mockResponse.data } returns mockListMovie
        runTest {
            coEvery { dataSource.getMovieList() } returns mockResponse
            repository.getMoviesNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(301)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getMovieList() }
            }
        }
    }

    @Test
    fun getMovieNowPlaying_error() {
        runTest {
            coEvery { dataSource.getMovieList() } throws IllegalStateException("Error")
            repository.getMoviesNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(301)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getMovieList() }
            }
        }
    }

    @Test
    fun getMovieNowPlaying_empty() {
        val mockListMovie = listOf<MovieListResponse>()
        val mockResponse = mockk<MovieNowPlayingResponse>()
        coEvery { mockResponse.data } returns mockListMovie
        runTest {
            coEvery { dataSource.getMovieList() } returns mockResponse
            repository.getMoviesNowPlaying().map {
                delay(100)
                it
            }.test {
                delay(301)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getMovieList() }
            }
        }
    }
}
