package com.nafi.movflix.presentation.viewmore

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nafi.movflix.data.repository.ViewMorePagingRepository
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse
import kotlinx.coroutines.flow.Flow

class ViewMoreViewModel(private val extras: Bundle?, private val repository: ViewMorePagingRepository) : ViewModel() {
    val header = extras?.getString(ViewMoreActivity.HEADER)

    fun topRatedMovies(): Flow<PagingData<MovieListResponse>> = repository.getTopRatedList().cachedIn(viewModelScope)

    fun nowPlayingMovies(): Flow<PagingData<MovieListResponse>> = repository.getNowPlayingList().cachedIn(viewModelScope)

    fun popularMovies(): Flow<PagingData<MovieListResponse>> = repository.getPopularList().cachedIn(viewModelScope)

    fun upComingMovies(): Flow<PagingData<MovieListResponse>> = repository.getUpComingList().cachedIn(viewModelScope)
}
