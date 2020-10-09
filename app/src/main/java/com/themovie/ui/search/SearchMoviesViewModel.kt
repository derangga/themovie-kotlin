package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldebaran.data.network.source.SearchMoviePagingFactory
import com.aldebaran.data.network.source.SearchMoviePagingSource
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.domain.Result.Status

class SearchMoviesViewModel @ViewModelInject constructor (
    private val remote: MovieRemoteSource
): ViewModel() {

    private val searchLiveData: LiveData<PagedList<MovieResponse>>
    private val uiList by lazy { MediatorLiveData<PagedList<MovieResponse>>() }
    private val searchSourceFactory by lazy { SearchMoviePagingFactory(viewModelScope, remote, query) }

    companion object{
        var query = ""
    }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()
        searchLiveData = LivePagedListBuilder(searchSourceFactory, pageConfig).build()
    }


    fun getSearchMovies(): MutableLiveData<PagedList<MovieResponse>>{
        uiList.addSource(searchLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(searchLiveData)
    }

    fun getLoadState(): LiveData<Status> {
        return Transformations.switchMap(
            searchSourceFactory.getResultSearch(),
            SearchMoviePagingSource::loadState
        )
    }

    fun retry(){
        searchSourceFactory.getResultSearch().value?.retry()
    }

    fun refresh(){
        searchSourceFactory.getResultSearch().value?.invalidate()
    }
}