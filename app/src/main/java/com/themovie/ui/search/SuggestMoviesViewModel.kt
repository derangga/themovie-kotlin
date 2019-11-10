package com.themovie.ui.search

import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.repos.fromapi.search.SearchRepos
import kotlinx.coroutines.launch
import java.lang.Exception

class SuggestMoviesViewModel(private val searchRepos: SearchRepos): ViewModel() {

    private val suggestLiveData = MutableLiveData<List<Movies>?>()
    private val loadDataStatus = MutableLiveData<LoadDataState>()


    fun getSuggestMovies(query: String): MutableLiveData<List<Movies>?> {
        viewModelScope.launch {
            try {
                val response = searchRepos.getSuggestionMoviesSearch(query)
                if(response.isSuccessful){
                    suggestLiveData.value = response.body()?.movies
                    loadDataStatus.value = LoadDataState.LOADED
                } else loadDataStatus.value = LoadDataState.ERROR
            } catch (e: Exception){
                loadDataStatus.value = LoadDataState.ERROR
            }
        }

        return suggestLiveData
    }

    fun getLoadStatus(): LiveData<LoadDataState> = loadDataStatus
}