package com.nafi.movflix.data.datasource.popular

import com.nafi.movflix.data.source.network.model.movie.MoviePopularResponse

interface MoviePopularDataSource {
    suspend fun getMovieList(): MoviePopularResponse
}
