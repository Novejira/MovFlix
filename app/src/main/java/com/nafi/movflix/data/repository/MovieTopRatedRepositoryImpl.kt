package com.nafi.movflix.data.repository

import com.nafi.movflix.data.datasource.toprated.MovieTopRatedDataSource
import com.nafi.movflix.data.mapper.toMovieList
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.utils.ResultWrapper
import com.nafi.movflix.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class MovieTopRatedRepositoryImpl(private val dataSource: MovieTopRatedDataSource) : MovieTopRatedRepository {
    override fun getTopRatedPlaying(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getMovieList().data.toMovieList()
        }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }
}
