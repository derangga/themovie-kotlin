package com.themovie.tmdb.ui.discover

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor (
    private val repos: Repository.TvRepos
): ViewModel() {

    fun getDiscoverTvPaging(): Flow<PagingData<Tv>> {
        return repos.getDiscoverTvPaging().cachedIn(viewModelScope)
    }
}