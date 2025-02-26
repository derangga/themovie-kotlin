package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor (
    private val repos: Repository.MovieRepos
) : ViewModel() {

    fun getDiscoverMoviePaging(genre: String): Flow<PagingData<Movie>> {
        return repos.getDiscoverMoviePaging(genre).cachedIn(viewModelScope)
    }
}