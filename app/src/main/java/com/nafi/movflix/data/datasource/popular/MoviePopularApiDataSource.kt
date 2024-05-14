package com.nafi.movflix.data.datasource.popular

import com.nafi.movflix.data.source.network.model.movie.MoviePopularResponse
import com.nafi.movflix.data.source.network.service.MovFlixApiService

class MoviePopularApiDataSource(private val service: MovFlixApiService) : MoviePopularDataSource {
    override suspend fun getMovieList(): MoviePopularResponse {
        return service.getPopularMovies()
    }
}
