package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.MovieResponse
import kotlinx.coroutines.launch

class SuggestMoviesViewModel @ViewModelInject constructor (
    private val remote: MovieRemoteSource
): ViewModel() {

    private val movieSearch: MutableLiveData<Result<DataList<MovieResponse>>> by lazy { MutableLiveData<Result<DataList<MovieResponse>>>() }

    fun fetchSuggestMovie(query: String){
        viewModelScope.launch {
            val response = remote.searchMovie(query, 1)
            movieSearch.value = response
        }
    }

    fun getSuggestMovies(): MutableLiveData<Result<DataList<MovieResponse>>> {
        return movieSearch
    }
}