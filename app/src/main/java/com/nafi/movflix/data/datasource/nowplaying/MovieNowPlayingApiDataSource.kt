package com.nafi.movflix.data.datasource.nowplaying

import com.nafi.movflix.data.source.network.model.movie.MovieNowPlayingResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService

class MovieNowPlayingApiDataSource(private val service: MovFlixApiService) : MovieNowPlayingDataSource {
    override suspend fun getMovieList(): MovieNowPlayingResponse {
        val minDate = "2024-04-01"
        val maxDate = "2024-05-15"
        return service.getNowPlayingMovies(minDate = minDate, maxDate = maxDate)
    }
}
