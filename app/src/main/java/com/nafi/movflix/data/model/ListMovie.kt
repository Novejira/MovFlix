package com.nafi.movflix.data.model

data class ListMovie(
    var id: Int? = null,
    val movieBackdropPath: String?,
    val movieId: Int?,
    val movieTitle: String?,
    val movieDesc: String?,
    val moviePopularity: Double?,
    val moviePosterPath: String?,
    val movieReleaseDate: String?,
    val movieVoteAverage: Double?,
)
