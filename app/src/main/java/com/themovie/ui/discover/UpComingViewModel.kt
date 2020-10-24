package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class UpComingViewModel @ViewModelInject constructor (
    private val repos: Repository.UpcomingRepos
): ViewModel() {

    fun getUpcomingMoviePaging(): Flow<PagingData<MovieResponse>> {
        return repos.getUpcomingMoviePaging().cachedIn(viewModelScope)
    }
}