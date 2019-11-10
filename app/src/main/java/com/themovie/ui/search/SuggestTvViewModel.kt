package com.themovie.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Tv
import com.themovie.repos.fromapi.search.SearchRepos
import kotlinx.coroutines.launch

class SuggestTvViewModel(private val searchRepos: SearchRepos): ViewModel() {

    private val suggestLiveData = MutableLiveData<List<Tv>?>()
    private val loadDataStatus = MutableLiveData<LoadDataState>()

    fun getSuggestTv(query: String): MutableLiveData<List<Tv>?> {
        viewModelScope.launch {
            try {
                val response = searchRepos.getSuggestionTvSearch(query)
                if(response.isSuccessful){
                    suggestLiveData.value = response.body()?.results
                    loadDataStatus.value = LoadDataState.LOADED
                } else loadDataStatus.value = LoadDataState.ERROR
            }catch (e: Exception){
                loadDataStatus.value = LoadDataState.ERROR
            }
        }
        return suggestLiveData
    }

    fun getLoadStatus(): LiveData<LoadDataState> = loadDataStatus
}