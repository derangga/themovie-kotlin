package com.themovie.ui.search

import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.Result
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class SuggestMoviesViewModel(private val apiRepository: ApiRepository): ViewModel() {

    private val movieSearch: MutableLiveData<Result<MoviesResponse>> by lazy { MutableLiveData<Result<MoviesResponse>>() }

    fun fetchSuggestMovie(query: String){
        viewModelScope.launch {
            val response = apiRepository.getSuggestionMoviesSearch(query)
            movieSearch.value = response
        }
    }

    fun getSuggestMovies(): MutableLiveData<Result<MoviesResponse>> {
        return movieSearch
    }
}