package com.nafi.movflix.data.mapper

import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.sourcelocal.ListMovieEntity

fun Movie?.toMovieEntity() =
    ListMovieEntity(
        movieId = this?.id,
        movieTitle = this?.title,
        movieDesc = this?.desc,
        movieBackdropPath = this?.backdropPath,
        moviePopularity = this?.popularity ?: 0.0,
        moviePosterPath = this?.posterPath,
        movieReleaseDate = this?.releaseDate,
        movieVoteAverage = this?.voteAverage ?: 0.0,
    )

fun ListMovieEntity?.toMovie() =
    Movie(
        id = this?.movieId,
        title = this?.movieTitle.orEmpty(),
        desc = this?.movieDesc.orEmpty(),
        backdropPath = this?.movieBackdropPath.orEmpty(),
        popularity = this?.moviePopularity ?: 0.0,
        posterPath = this?.moviePosterPath.orEmpty(),
        releaseDate = this?.movieReleaseDate.orEmpty(),
        voteAverage = this?.movieVoteAverage ?: 0.0,
    )

fun List<ListMovieEntity>.toMovieList() = this.map { it.toMovie() }
