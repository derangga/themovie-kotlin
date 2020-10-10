package com.themovie.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldebaran.data.network.source.SearchTvPagingFactory
import com.aldebaran.data.network.source.SearchTvPagingSource
import com.aldebaran.domain.Result.*
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource

class SearchTvViewModel @ViewModelInject constructor (
    private val remote: TvRemoteSource
): ViewModel() {

    private val searchLiveData: LiveData<PagedList<TvResponse>>
    private val uiList by lazy { MediatorLiveData<PagedList<TvResponse>>() }
    private val searchSourceFactory by lazy { SearchTvPagingFactory(viewModelScope, remote, query) }

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

    fun getSearchTvResult(): MutableLiveData<PagedList<TvResponse>>{
        uiList.addSource(searchLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(searchLiveData)
    }

    fun getLoadState(): LiveData<Status>{
        return Transformations.switchMap(
                searchSourceFactory.getResultSearch(),
                SearchTvPagingSource::loadState
            )
    }

    fun retry(){
        searchSourceFactory.getResultSearch().value?.retry()
    }

    fun refresh(){
        searchSourceFactory.getResultSearch().value?.invalidate()
    }
}