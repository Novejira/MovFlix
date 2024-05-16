package com.nafi.movflix.data.sourcelocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list")
data class ListMovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "movie_id")
    var movieId: Int?,
    @ColumnInfo(name = "movie_Title")
    var movieTitle: String?,
    @ColumnInfo(name = "movie_Desc")
    var movieDesc: String?,
    @ColumnInfo(name = "movie_BackdropPath")
    var movieBackdropPath: String?,
    @ColumnInfo(name = "movie_Popularity")
    var moviePopularity: Double?,
    @ColumnInfo(name = "movie_PosterPath")
    var moviePosterPath: String?,
    @ColumnInfo(name = "movie_ReleaseDate")
    var movieReleaseDate: String?,
    @ColumnInfo(name = "movie_VoteAverage")
    var movieVoteAverage: Double?,
)
