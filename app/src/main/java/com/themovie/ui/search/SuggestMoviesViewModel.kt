package com.themovie.ui.search

import androidx.lifecycle.*
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.repos.RemoteSource
import com.themovie.restapi.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class SuggestMoviesViewModel @Inject constructor (
    private val remote: RemoteSource
): ViewModel() {

    private val movieSearch: MutableLiveData<Result<MoviesResponse>> by lazy { MutableLiveData<Result<MoviesResponse>>() }

    fun fetchSuggestMovie(query: String){
        viewModelScope.launch {
            val response = remote.getSuggestSearchMovie(query)
            movieSearch.value = response
        }
    }

    fun getSuggestMovies(): MutableLiveData<Result<MoviesResponse>> {
        return movieSearch
    }
}