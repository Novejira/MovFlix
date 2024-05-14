package com.nafi.movflix.di

import com.nafi.movflix.data.datasource.nowplaying.MovieNowPlayingApiDataSource
import com.nafi.movflix.data.datasource.nowplaying.MovieNowPlayingDataSource
import com.nafi.movflix.data.datasource.popular.MoviePopularApiDataSource
import com.nafi.movflix.data.datasource.popular.MoviePopularDataSource
import com.nafi.movflix.data.datasource.toprated.MovieTopRatedApiDataSource
import com.nafi.movflix.data.datasource.toprated.MovieTopRatedDataSource
import com.nafi.movflix.data.datasource.upcoming.MovieUpComingApiDataSource
import com.nafi.movflix.data.datasource.upcoming.MovieUpComingDataSource
import com.nafi.movflix.data.repository.MovieNowPlayingRepository
import com.nafi.movflix.data.repository.MovieNowPlayingRepositoryImpl
import com.nafi.movflix.data.repository.MoviePopularRepository
import com.nafi.movflix.data.repository.MoviePopularRepositoryImpl
import com.nafi.movflix.data.repository.MovieTopRatedRepository
import com.nafi.movflix.data.repository.MovieTopRatedRepositoryImpl
import com.nafi.movflix.data.repository.MovieUpComingRepository
import com.nafi.movflix.data.repository.MovieUpComingRepositoryImpl
import com.nafi.movflix.data.source.network.service.MovFlixApiService
import com.nafi.movflix.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<MovFlixApiService> { MovFlixApiService.invoke() }
        }

    private val localModule =
        module {
        }

    private val datasource =
        module {
            single<MovieNowPlayingDataSource> { MovieNowPlayingApiDataSource(get()) }
            single<MoviePopularDataSource> { MoviePopularApiDataSource(get()) }
            single<MovieTopRatedDataSource> { MovieTopRatedApiDataSource(get()) }
            single<MovieUpComingDataSource> { MovieUpComingApiDataSource(get()) }
        }

    private val repository =
        module {
            single<MovieNowPlayingRepository> { MovieNowPlayingRepositoryImpl(get()) }
            single<MoviePopularRepository> { MoviePopularRepositoryImpl(get()) }
            single<MovieTopRatedRepository> { MovieTopRatedRepositoryImpl(get()) }
            single<MovieUpComingRepository> { MovieUpComingRepositoryImpl(get()) }
        }

    private val viewModel =
        module {
            viewModelOf(::HomeViewModel)
        }

    val modules =
        listOf<Module>(
            networkModule,
            localModule,
            datasource,
            repository,
            viewModel,
        )
}
