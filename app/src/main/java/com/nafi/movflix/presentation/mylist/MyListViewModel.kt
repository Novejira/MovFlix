package com.nafi.movflix.presentation.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nafi.movflix.data.model.Movie
import com.nafi.movflix.data.repository.ListMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyListViewModel(private val listRepository: ListMovieRepository) : ViewModel() {
    fun getAllList() = listRepository.getAllList().asLiveData(Dispatchers.IO)

    fun deleteList(list: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteList(list)
        }
    }

    fun addToList(detail: Movie) = listRepository.addList(detail).asLiveData(Dispatchers.IO)

    fun checkMovieList(movieId: Int?) = listRepository.checkListById(movieId).asLiveData(Dispatchers.IO)

    fun removeFromList(movieId: Int?) = listRepository.removeList(movieId).asLiveData(Dispatchers.IO)
}
