package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldebaran.data.network.source.UpcomingPagingSource
import com.aldebaran.data.network.source.UpcomingPagingFactory
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class UpComingViewModel @ViewModelInject constructor (remote: MovieRemoteSource): ViewModel() {
    private val upcomingLiveData: LiveData<PagedList<MovieResponse>>
    private val uiList = MediatorLiveData<PagedList<MovieResponse>>()
    private val upcomingSourceFactory by lazy {
        UpcomingPagingFactory(
            viewModelScope,
            remote
        )
    }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()
        upcomingLiveData = LivePagedListBuilder(upcomingSourceFactory, pageConfig).build()
    }

    fun getUpcomingData(): MediatorLiveData<PagedList<MovieResponse>> {
        uiList.addSource(upcomingLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(upcomingLiveData)
    }

    fun getLoadState(): LiveData<Result.Status> {
        return Transformations.switchMap(upcomingSourceFactory.getUpcomingDataSource(), UpcomingPagingSource::loadState)
    }

    fun retry(){
        upcomingSourceFactory.getUpcomingDataSource().value?.retry()
    }

    fun refresh(){
        upcomingSourceFactory.getUpcomingDataSource().value?.invalidate()
    }
}