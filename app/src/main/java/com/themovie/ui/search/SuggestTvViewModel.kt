package com.themovie.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Tv
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SuggestTvViewModel(private val apiRepository: ApiRepository): ViewModel() {

    private val suggestLiveData = MutableLiveData<List<Tv>?>()
    private val loadDataStatus = MutableLiveData<LoadDataState>()

    fun fetchSuggestTv(query: String){
        viewModelScope.launch {
            apiRepository.getSuggestionTvSearch(query, object: ApiCallback<TvResponse>{
                override fun onSuccessRequest(response: TvResponse?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    suggestLiveData.value = response?.results
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

    fun getSuggestTv(): MutableLiveData<List<Tv>?> {
        return suggestLiveData
    }

    fun getLoadStatus(): LiveData<LoadDataState> = loadDataStatus
}