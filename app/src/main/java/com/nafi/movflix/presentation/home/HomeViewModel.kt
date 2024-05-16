package com.nafi.movflix.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.repository.ListMovieRepository
import com.nafi.movflix.data.repository.MovieNowPlayingRepository
import com.nafi.movflix.data.repository.MoviePopularRepository
import com.nafi.movflix.data.repository.MovieTopRatedRepository
import com.nafi.movflix.data.repository.MovieUpComingRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val movieNowPlayingRepository: MovieNowPlayingRepository,
    private val moviePopularRepository: MoviePopularRepository,
    private val movieTopRatedRepository: MovieTopRatedRepository,
    private val movieUpComingRepository: MovieUpComingRepository,
    private val listMovieRepository: ListMovieRepository,
) : ViewModel() {
    fun getMoviesNowPlaying() = movieNowPlayingRepository.getMoviesNowPlaying().asLiveData(Dispatchers.IO)

    fun getMoviesPopular() = moviePopularRepository.getPopularPlaying().asLiveData(Dispatchers.IO)

    fun getMoviesTopRated() = movieTopRatedRepository.getTopRatedPlaying().asLiveData(Dispatchers.IO)

    fun getMoviesUpComing() = movieUpComingRepository.getUpComingPlaying().asLiveData(Dispatchers.IO)

    fun addToList(detail: Movie) = listMovieRepository.addList(detail).asLiveData(Dispatchers.IO)

    fun checkMovieList(movieId: Int?) = listMovieRepository.checkListById(movieId).asLiveData(Dispatchers.IO)

    fun removeFromList(movieId: Int?) = listMovieRepository.removeList(movieId).asLiveData(Dispatchers.IO)
}
