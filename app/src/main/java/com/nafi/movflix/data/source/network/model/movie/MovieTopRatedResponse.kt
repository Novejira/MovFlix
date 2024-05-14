package com.nafi.movflix.data.source.network.model.movie

import com.google.gson.annotations.SerializedName

data class MovieTopRatedResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val data: List<MovieListResponse>,
)
