package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class TvViewModel @ViewModelInject constructor (
    private val repos: Repository.TvRepos
): ViewModel() {

    fun getDiscoverTvPaging(): Flow<PagingData<Tv>> {
        return repos.getDiscoverTvPaging().cachedIn(viewModelScope)
    }
}