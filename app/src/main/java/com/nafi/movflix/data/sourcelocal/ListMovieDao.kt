package com.nafi.movflix.data.sourcelocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlin.collections.List

@Dao
interface ListMovieDao {
    @Query("SELECT * FROM LIST")
    fun getAllList(): Flow<List<ListMovieEntity>>

    @Query("SELECT * FROM LIST WHERE movie_id = :movieId ")
    fun checkListById(movieId: Int?): Flow<List<ListMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(list: ListMovieEntity): Long

    @Delete
    suspend fun deleteList(list: ListMovieEntity): Int

    @Query("DELETE FROM LIST WHERE movie_id = :movieId")
    suspend fun removeList(movieId: Int?): Int

    @Query("DELETE FROM LIST")
    suspend fun deleteAll()
}
