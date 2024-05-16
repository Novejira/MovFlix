package com.nafi.movflix.data.mapper

import com.nafi.movflix.data.model.ListMovie
import com.nafi.movflix.data.sourcelocal.ListMovieEntity

fun ListMovie?.toListEntity() =
    ListMovieEntity(
        movieId = this?.movieId,
        movieTitle = this?.movieTitle,
        movieDesc = this?.movieDesc,
        movieBackdropPath = this?.movieBackdropPath,
        moviePopularity = this?.moviePopularity,
        moviePosterPath = this?.moviePosterPath,
        movieReleaseDate = this?.movieReleaseDate,
        movieVoteAverage = this?.movieVoteAverage,
    )

fun ListMovieEntity?.toList() =
    ListMovie(
        movieId = this?.movieId,
        movieTitle = this?.movieTitle,
        movieDesc = this?.movieDesc,
        movieBackdropPath = this?.movieBackdropPath,
        moviePopularity = this?.moviePopularity,
        moviePosterPath = this?.moviePosterPath,
        movieReleaseDate = this?.movieReleaseDate,
        movieVoteAverage = this?.movieVoteAverage,
    )

fun List<ListMovieEntity>.toListMovie() = this.map { it.toList() }
