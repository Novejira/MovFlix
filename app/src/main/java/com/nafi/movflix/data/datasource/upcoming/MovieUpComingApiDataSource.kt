package com.nafi.movflix.data.datasource.upcoming

import com.nafi.movflix.data.source.network.model.movie.MovieUpComingResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService

class MovieUpComingApiDataSource(private val service: MovFlixApiService) : MovieUpComingDataSource {
    override suspend fun getMovieList(): MovieUpComingResponse {
        val minDate = "2024-05-15"
        val maxDate = "2024-06-05"
        return service.getUpComingMovies(minDate = minDate, maxDate = maxDate)
    }
}
