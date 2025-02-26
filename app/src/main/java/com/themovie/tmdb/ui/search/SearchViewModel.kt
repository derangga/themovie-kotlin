package com.themovie.tmdb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: Repository.MovieRepos,
    private val tvRepository: Repository.TvRepos
) : ViewModel() {

    fun getDiscoverMoviePaging(query: String): Flow<PagingData<Movie>> {
        return movieRepository.searchMoovie(query).cachedIn(viewModelScope)
    }

    fun getDiscoverTvPaging(query: String): Flow<PagingData<Tv>> {
        return tvRepository.searchTvShow(query).cachedIn(viewModelScope)
    }
}