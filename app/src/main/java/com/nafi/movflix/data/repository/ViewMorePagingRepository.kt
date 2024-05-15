package com.nafi.movflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nafi.movflix.data.paging.NowPlayingPagingSource
import com.nafi.movflix.data.paging.PopularPagingSource
import com.nafi.movflix.data.paging.TopRatedPagingSource
import com.nafi.movflix.data.paging.UpComingPagingSource
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService
import kotlinx.coroutines.flow.Flow

interface ViewMorePagingRepository {
    fun getTopRatedList(): Flow<PagingData<MovieListResponse>>

    fun getUpComingList(): Flow<PagingData<MovieListResponse>>

    fun getPopularList(): Flow<PagingData<MovieListResponse>>

    fun getNowPlayingList(): Flow<PagingData<MovieListResponse>>
}

class ViewMorePagingRepositoryImpl(private val service: MovFlixApiService) : ViewMorePagingRepository {
    override fun getTopRatedList(): Flow<PagingData<MovieListResponse>> =
        Pager(
            pagingSourceFactory = { TopRatedPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getUpComingList(): Flow<PagingData<MovieListResponse>> =
        Pager(
            pagingSourceFactory = { UpComingPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getPopularList(): Flow<PagingData<MovieListResponse>> =
        Pager(
            pagingSourceFactory = { PopularPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getNowPlayingList(): Flow<PagingData<MovieListResponse>> =
        Pager(
            pagingSourceFactory = { NowPlayingPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow
}
