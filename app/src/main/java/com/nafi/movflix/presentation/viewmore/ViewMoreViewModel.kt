package com.nafi.movflix.presentation.viewmore

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.repository.ListMovieRepository
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.repository.ViewMorePagingRepository
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ViewMoreViewModel(private val extras: Bundle?, private val repository: ViewMorePagingRepository, private val listRepository: ListMovieRepository) : ViewModel() {
    val header = extras?.getString(ViewMoreActivity.HEADER)

    fun topRatedMovies(): Flow<PagingData<Movie>> = repository.getTopRatedList().cachedIn(viewModelScope)

    fun nowPlayingMovies(): Flow<PagingData<Movie>> = repository.getNowPlayingList().cachedIn(viewModelScope)

    fun popularMovies(): Flow<PagingData<Movie>> = repository.getPopularList().cachedIn(viewModelScope)

    fun upComingMovies(): Flow<PagingData<MovieListResponse>> = repository.getUpComingList().cachedIn(viewModelScope)

    fun addToList(detail: Movie) = listRepository.addList(detail).asLiveData(Dispatchers.IO)

    fun checkMovieList(movieId: Int?) = listRepository.checkListById(movieId).asLiveData(Dispatchers.IO)

    fun removeFromList(movieId: Int?) = listRepository.removeList(movieId).asLiveData(Dispatchers.IO)
    fun upComingMovies(): Flow<PagingData<Movie>> = repository.getUpComingList().cachedIn(viewModelScope)
}
