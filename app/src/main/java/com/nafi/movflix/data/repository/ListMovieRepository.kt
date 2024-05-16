package com.nafi.movflix.data.repository

import com.nafi.movflix.data.datasource.listmovie.ListMovieDataSource
import com.nafi.movflix.data.mapper.toMovieEntity
import com.nafi.movflix.data.mapper.toMovieList
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.sourcelocal.ListMovieEntity
import com.nafi.movflix.utils.ResultWrapper
import com.nafi.movflix.utils.proceed
import com.nafi.movflix.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface ListMovieRepository {
    fun getAllList(): Flow<ResultWrapper<List<Movie>>>

    fun checkListById(movieId: Int?): Flow<List<ListMovieEntity>>

    fun addList(movie: Movie): Flow<ResultWrapper<Boolean>>

    fun deleteList(movie: Movie): Flow<ResultWrapper<Boolean>>

    fun removeList(movieId: Int?): Flow<ResultWrapper<Boolean>>

    fun deleteAll(): Flow<ResultWrapper<Boolean>>
}

class ListMovieRepositoryImpl(private val datasource: ListMovieDataSource) : ListMovieRepository {
    override fun getAllList(): Flow<ResultWrapper<List<Movie>>> {
        return datasource.getAllList()
            .map {
                proceed {
                    val result = it.toMovieList()
                    result
                }
            }.map {
                if (it.payload?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun checkListById(movieId: Int?): Flow<List<ListMovieEntity>> {
        return datasource.checkListById(movieId)
    }

    override fun addList(movie: Movie): Flow<ResultWrapper<Boolean>> {
        return movie.id.let { movieId ->
            proceedFlow {
                val affectedRow =
                    datasource.addList(
                        ListMovieEntity(
                            movieId = movieId,
                            moviePosterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                            movieTitle = movie.title,
                            movieDesc = movie.desc,
                            movieBackdropPath = movie.backdropPath,
                            moviePopularity = movie.popularity,
                            movieReleaseDate = movie.releaseDate,
                            movieVoteAverage = movie.voteAverage,
                        ),
                    )
                affectedRow > 0
            }
        }
    }

    override fun deleteList(movie: Movie): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { datasource.deleteList(movie.toMovieEntity()) > 0 }
    }

    override fun removeList(movieId: Int?): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { datasource.removeList(movieId) > 0 }
    }

    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            datasource.deleteAll()
            true
        }
    }
}
