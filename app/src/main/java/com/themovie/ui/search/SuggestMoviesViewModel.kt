package com.themovie.ui.search

import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class SuggestMoviesViewModel(private val apiRepository: ApiRepository): ViewModel() {

    private val suggestLiveData = MutableLiveData<List<Movies>?>()
    private val loadDataStatus = MutableLiveData<LoadDataState>()

    fun fetchSuggestMovie(query: String){
        viewModelScope.launch {
            apiRepository.getSuggestionMoviesSearch(query, object: ApiCallback<MoviesResponse>{
                override fun onSuccessRequest(response: MoviesResponse?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    suggestLiveData.value = response?.movies
                }

                override fun onErrorRequest(errorBody: ResponseBody?) {
                    loadDataStatus.value = LoadDataState.ERROR
                }

                override fun onFailure(e: Exception) {
                    loadDataStatus.value = LoadDataState.ERROR
                }
            })
        }
    }

    fun getSuggestMovies(): MutableLiveData<List<Movies>?> {
        return suggestLiveData
    }

    fun getLoadStatus(): LiveData<LoadDataState> = loadDataStatus
}