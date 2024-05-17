package com.nafi.movflix.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nafi.movflix.data.repository.ListMovieRepository
import com.nafi.movflix.data.repository.MovieNowPlayingRepository
import com.nafi.movflix.data.repository.MoviePopularRepository
import com.nafi.movflix.data.repository.MovieTopRatedRepository
import com.nafi.movflix.data.repository.MovieUpComingRepository
import com.nafi.movflix.tools.MainCoroutineRule
import com.nafi.movflix.tools.getOrAwaitValue
import com.nafi.movflix.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var movieNowPlayingRepository: MovieNowPlayingRepository

    @MockK
    lateinit var moviePopularRepository: MoviePopularRepository

    @MockK
    lateinit var movieTopRatedRepository: MovieTopRatedRepository

    @MockK
    lateinit var movieUpComingRepository: MovieUpComingRepository

    @MockK
    lateinit var listMovieRepository: ListMovieRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    movieNowPlayingRepository,
                    moviePopularRepository,
                    movieTopRatedRepository,
                    movieUpComingRepository,
                    listMovieRepository,
                ),
            )
    }

    @Test
    fun getMoviesNowPlaying() {
        every { movieNowPlayingRepository.getMoviesNowPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { movieNowPlayingRepository.getMoviesNowPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMoviesNowPlaying().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieNowPlayingRepository.getMoviesNowPlaying() }
    }

    @Test
    fun getMoviesPopular() {
        every { moviePopularRepository.getPopularPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { moviePopularRepository.getPopularPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMoviesPopular().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { moviePopularRepository.getPopularPlaying() }
    }

    @Test
    fun getMoviesTopRated() {
        every { movieTopRatedRepository.getTopRatedPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { movieTopRatedRepository.getTopRatedPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMoviesTopRated().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieTopRatedRepository.getTopRatedPlaying() }
    }

    @Test
    fun getMoviesUpComing() {
        every { movieUpComingRepository.getUpComingPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { movieUpComingRepository.getUpComingPlaying() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMoviesUpComing().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { movieUpComingRepository.getUpComingPlaying() }
    }
}
