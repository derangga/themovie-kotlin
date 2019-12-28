package com.themovie.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.Result
import kotlinx.coroutines.launch

class SuggestTvViewModel(private val apiRepository: ApiRepository): ViewModel() {

    private val tvSearch: MutableLiveData<Result<TvResponse>> by lazy { MutableLiveData<Result<TvResponse>>() }

    fun fetchSuggestTv(query: String) {
        viewModelScope.launch {
            val response = apiRepository.getSuggestionTvSearch(query)
            tvSearch.value = response
        }
    }

    fun getSuggestTv(): MutableLiveData<Result<TvResponse>> {
        return tvSearch
    }

}