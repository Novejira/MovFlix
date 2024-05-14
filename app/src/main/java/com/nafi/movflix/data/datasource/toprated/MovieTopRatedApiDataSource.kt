package com.nafi.movflix.data.datasource.toprated

import com.nafi.movflix.data.source.network.model.movie.MovieTopRatedResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService

class MovieTopRatedApiDataSource(private val service: MovFlixApiService) : MovieTopRatedDataSource {
    override suspend fun getMovieList(): MovieTopRatedResponse {
        return service.getTopRatedMovies()
    }
}
