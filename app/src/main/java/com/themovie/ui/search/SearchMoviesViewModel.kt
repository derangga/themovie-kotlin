package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class SearchMoviesViewModel @ViewModelInject constructor (
    private val repos: Repository.MovieRepos
): ViewModel() {

    fun getDiscoverMoviePaging(query: String): Flow<PagingData<MovieResponse>> {
        return repos.searchMoovie(query).cachedIn(viewModelScope)
    }
}