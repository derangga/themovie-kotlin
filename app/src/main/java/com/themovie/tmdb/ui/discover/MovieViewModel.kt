package com.themovie.tmdb.ui.discover

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (
    private val repos: Repository.MovieRepos
) : ViewModel() {

    fun getDiscoverMoviePaging(genre: String): Flow<PagingData<Movie>> {
        return repos.getDiscoverMoviePaging(genre).cachedIn(viewModelScope)
    }
}