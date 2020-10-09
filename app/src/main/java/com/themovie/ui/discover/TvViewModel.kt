package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldebaran.data.network.source.TvPagingFactory
import com.aldebaran.data.network.source.TvPagingSource
import com.aldebaran.domain.Result.*
import com.aldebaran.domain.entities.remote.TvResponse
import com.aldebaran.domain.repository.remote.TvRemoteSource

class TvViewModel @ViewModelInject constructor (remote: TvRemoteSource): ViewModel() {

    private var tvLiveData: LiveData<PagedList<TvResponse>>
    private val uiList = MediatorLiveData<PagedList<TvResponse>>()
    private val tvSourceFactory by lazy {
        TvPagingFactory(
            viewModelScope,
            remote
        )
    }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()

        tvLiveData = LivePagedListBuilder(tvSourceFactory, pageConfig).build()
    }

    fun getTvLiveData(): MediatorLiveData<PagedList<TvResponse>> {
        uiList.addSource(tvLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(tvLiveData)
    }


    fun getLoadState(): LiveData<Status> {
        return Transformations
            .switchMap(tvSourceFactory.getTvDataSource(), TvPagingSource::loadState)
    }

    fun retry(){
        tvSourceFactory.getTvDataSource().value?.retry()
    }

    fun refresh(){
        tvSourceFactory.getTvDataSource().value?.invalidate()
    }
}