package com.nafi.movflix.data.repository

import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface MoviePopularRepository {
    fun getPopularPlaying(): Flow<ResultWrapper<List<Movie>>>
}
