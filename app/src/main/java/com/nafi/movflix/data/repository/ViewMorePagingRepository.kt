package com.nafi.movflix.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.paging.NowPlayingPagingSource
import com.nafi.movflix.data.paging.PopularPagingSource
import com.nafi.movflix.data.paging.TopRatedPagingSource
import com.nafi.movflix.data.paging.UpComingPagingSource
import com.nafi.movflix.data.source.network.service.MovFlixApiService
import kotlinx.coroutines.flow.Flow

interface ViewMorePagingRepository {
    fun getTopRatedList(): Flow<PagingData<Movie>>

    fun getUpComingList(): Flow<PagingData<Movie>>

    fun getPopularList(): Flow<PagingData<Movie>>

    fun getNowPlayingList(): Flow<PagingData<Movie>>
}

class ViewMorePagingRepositoryImpl(private val service: MovFlixApiService) : ViewMorePagingRepository {
    override fun getTopRatedList(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { TopRatedPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getUpComingList(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { UpComingPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getPopularList(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { PopularPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow

    override fun getNowPlayingList(): Flow<PagingData<Movie>> =
        Pager(
            pagingSourceFactory = { NowPlayingPagingSource(service) },
            config =
                PagingConfig(
                    pageSize = 20,
                ),
        ).flow
}
