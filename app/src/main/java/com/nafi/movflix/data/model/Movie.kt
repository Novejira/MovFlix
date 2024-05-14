package com.nafi.movflix.data.model

data class Movie(
    val backdropPath: String,
    val id: Int?,
    val title: String,
    val desc: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
)
