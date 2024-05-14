package com.nafi.movflix.data.datasource.toprated

import com.nafi.movflix.data.source.network.model.movie.MovieTopRatedResponse

interface MovieTopRatedDataSource {
    suspend fun getMovieList(): MovieTopRatedResponse
}
