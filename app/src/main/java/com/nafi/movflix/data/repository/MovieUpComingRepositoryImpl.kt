package com.nafi.movflix.data.repository

import com.nafi.movflix.data.datasource.upcoming.MovieUpComingDataSource
import com.nafi.movflix.data.mapper.toMovieList
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.utils.ResultWrapper
import com.nafi.movflix.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class MovieUpComingRepositoryImpl(private val dataSource: MovieUpComingDataSource) : MovieUpComingRepository {
    override fun getUpComingPlaying(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getMovieList().data.toMovieList()
        }
            .onStart {
                emit(ResultWrapper.Loading())
            }
    }
}
