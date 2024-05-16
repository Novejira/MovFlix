package com.nafi.movflix.data.datasource.listmovie

import com.nafi.movflix.data.sourcelocal.ListMovieDao
import com.nafi.movflix.data.sourcelocal.ListMovieEntity
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List

interface ListMovieDataSource {
    fun getAllList(): Flow<List<ListMovieEntity>>

    fun checkListById(movieId: Int?): Flow<List<ListMovieEntity>>

    suspend fun addList(list: ListMovieEntity): Long

    suspend fun deleteList(list: ListMovieEntity): Int

    suspend fun removeList(movieId: Int?): Int

    suspend fun deleteAll()
}

class ListMovieDataSourceImpl(private val dao: ListMovieDao) : ListMovieDataSource {
    override fun getAllList(): Flow<List<ListMovieEntity>> = dao.getAllList()

    override fun checkListById(movieId: Int?): Flow<List<ListMovieEntity>> = dao.checkListById(movieId)

    override suspend fun addList(list: ListMovieEntity): Long = dao.addList(list)

    override suspend fun deleteList(list: ListMovieEntity): Int = dao.deleteList(list)

    override suspend fun removeList(movieId: Int?): Int = dao.removeList(movieId)

    override suspend fun deleteAll() = dao.deleteAll()
}
