package com.themovie.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.repos.RemoteSource
import com.themovie.restapi.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class SuggestTvViewModel @Inject constructor (
    private val remote: RemoteSource
): ViewModel() {

    private val tvSearch: MutableLiveData<Result<TvResponse>> by lazy { MutableLiveData<Result<TvResponse>>() }

    fun fetchSuggestTv(query: String) {
        viewModelScope.launch {
            val response = remote.getSuggestSearchTv(query)
            tvSearch.value = response
        }
    }

    fun getSuggestTv(): MutableLiveData<Result<TvResponse>> {
        return tvSearch
    }

}