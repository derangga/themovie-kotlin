package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.network.Result.*
import kotlinx.coroutines.launch

class SuggestViewModel @ViewModelInject constructor(
    private val movieRemoteSource: MovieRemoteSource,
    private val tvRemoteSource: TvRemoteSource
) : ViewModel() {

    private val _movieSearch: MutableLiveData<List<Movie>> by lazy { MutableLiveData<List<Movie>>() }
    val movieSearch: LiveData<List<Movie>> get() = _movieSearch
    private val _tvSearch: MutableLiveData<List<Tv>> by lazy { MutableLiveData<List<Tv>>() }
    val tvSearch: LiveData<List<Tv>> get() = _tvSearch

    fun fetchSuggestTv(query: String) {
        viewModelScope.launch {
            when (val result = tvRemoteSource.searchTv(query, 1)) {
                is Success -> _tvSearch.value = result.data

                is Error -> Unit
            }
        }
    }

    fun fetchSuggestMovie(query: String){
        viewModelScope.launch {
            when (val result = movieRemoteSource.searchMovie(query, 1)){
                is Success -> _movieSearch.value = result.data
                is Error -> Unit
            }
        }
    }
}