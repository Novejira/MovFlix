package com.nafi.movflix.data.datasource.upcoming

import com.nafi.movflix.data.source.network.model.movie.MovieUpComingResponse

interface MovieUpComingDataSource {
    suspend fun getMovieList(): MovieUpComingResponse
}
