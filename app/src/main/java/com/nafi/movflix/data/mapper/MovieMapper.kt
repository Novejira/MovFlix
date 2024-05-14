package com.nafi.movflix.data.mapper

import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.source.network.model.movie.MovieListResponse

fun MovieListResponse?.toMovie() =
    Movie(
        backdropPath = this?.backdropPath.orEmpty(),
        id = this?.id,
        title = this?.title.orEmpty(),
        desc = this?.desc.orEmpty(),
        popularity = this?.popularity ?: 0.0,
        posterPath = this?.posterPath.orEmpty(),
        releaseDate = this?.releaseDate.orEmpty(),
        voteAverage = this?.voteAverage ?: 0.0,
    )

fun Collection<MovieListResponse>?.toMovieList() =
    this?.map {
        it.toMovie()
    } ?: listOf()
