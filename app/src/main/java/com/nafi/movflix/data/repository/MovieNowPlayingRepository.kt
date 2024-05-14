package com.nafi.movflix.data.repository

import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MovieNowPlayingRepository {
    fun getMoviesNowPlaying(): Flow<ResultWrapper<List<Movie>>>
}
