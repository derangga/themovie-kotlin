package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import kotlinx.coroutines.launch

class SuggestTvViewModel @ViewModelInject constructor (
    private val remote: TvRemoteSource
): ViewModel() {

    private val tvSearch: MutableLiveData<Result<DataList<TvResponse>>> by lazy { MutableLiveData<Result<DataList<TvResponse>>>() }

    fun fetchSuggestTv(query: String) {
        viewModelScope.launch {
            val response = remote.searchTv(query, 1)
            tvSearch.value = response
        }
    }

    fun getSuggestTv(): MutableLiveData<Result<DataList<TvResponse>>> {
        return tvSearch
    }

}