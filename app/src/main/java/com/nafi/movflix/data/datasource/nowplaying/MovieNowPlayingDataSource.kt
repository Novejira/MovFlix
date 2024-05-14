package com.nafi.movflix.data.datasource.nowplaying

import com.nafi.movflix.data.source.network.model.movie.MovieNowPlayingResponse

interface MovieNowPlayingDataSource {
    suspend fun getMovieList(): MovieNowPlayingResponse
}
