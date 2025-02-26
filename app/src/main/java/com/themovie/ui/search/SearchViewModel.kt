package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldebaran.domain.entities.ui.Movie
import com.aldebaran.domain.entities.ui.Tv
import com.aldebaran.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class SearchViewModel @ViewModelInject constructor(
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