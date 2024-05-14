package com.nafi.movflix.data.repository

import com.nafi.movflix.data.datasource.popular.MoviePopularDataSource
import com.nafi.movflix.data.mapper.toMovieList
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.utils.ResultWrapper
import com.nafi.movflix.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class MoviePopularRepositoryImpl(private val dataSource: MoviePopularDataSource) : MoviePopularRepository {
    override fun getPopularPlaying(): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getMovieList().data.toMovieList()
        }
            .onStart {
                emit(ResultWrapper.Loading())
                delay(1000)
            }
    }
}
