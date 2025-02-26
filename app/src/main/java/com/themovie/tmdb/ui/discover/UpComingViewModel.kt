package com.themovie.tmdb.ui.discover

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor (
    private val repos: Repository.UpcomingRepos
): ViewModel() {

    fun getUpcomingMoviePaging(): Flow<PagingData<Movie>> {
        return repos.getUpcomingMoviePaging().cachedIn(viewModelScope)
    }
}