package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class SearchTvViewModel @ViewModelInject constructor (
    private val repos: Repository.TvRepos
): ViewModel() {

    fun getDiscoverTvPaging(query: String): Flow<PagingData<TvResponse>> {
        return repos.searchTvShow(query).cachedIn(viewModelScope)
    }
}