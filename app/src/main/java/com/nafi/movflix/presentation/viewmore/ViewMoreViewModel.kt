package com.nafi.movflix.presentation.viewmore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nafi.movflix.data.repository.ViewMorePagingRepository
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse
import kotlinx.coroutines.flow.Flow

class ViewMoreViewModel(private val repository: ViewMorePagingRepository) : ViewModel() {
    fun topRatedMovies(): Flow<PagingData<MovieListResponse>> = repository.getTopRatedList().cachedIn(viewModelScope)

    fun topNowPlaying(): Flow<PagingData<MovieListResponse>> = repository.getNowPlayingList().cachedIn(viewModelScope)

    fun topPopular(): Flow<PagingData<MovieListResponse>> = repository.getPopularList().cachedIn(viewModelScope)

    fun topUpComing(): Flow<PagingData<MovieListResponse>> = repository.getUpComingList().cachedIn(viewModelScope)
}
