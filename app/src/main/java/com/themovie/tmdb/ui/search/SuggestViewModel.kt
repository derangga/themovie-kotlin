package com.themovie.tmdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.datasource.repository.remote.TvRemoteSource
import com.themovie.core.network.Result.Success
import com.themovie.core.network.Result.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    private val movieRemoteSource: MovieRemoteSource,
    private val tvRemoteSource: TvRemoteSource
) : ViewModel() {

    private val _movieSearch: MutableLiveData<List<Movie>> by lazy { MutableLiveData<List<Movie>>() }
    val movieSearch: LiveData<List<Movie>> get() = _movieSearch
    private val _tvSearch: MutableLiveData<List<Tv>> by lazy { MutableLiveData<List<Tv>>() }
    val tvSearch: LiveData<List<Tv>> get() = _tvSearch

    private val _searchText by lazy { MutableLiveData<String>() }
    val searchText: LiveData<String> get() = _searchText

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

    fun searchMovieAndTv(query: String) {
        _searchText.value = query
    }
}