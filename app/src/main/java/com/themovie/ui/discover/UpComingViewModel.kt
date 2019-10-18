package com.themovie.ui.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.repos.fromapi.discover.UpcomingDataSource
import com.themovie.repos.fromapi.discover.UpcomingDataSourceFactory
import com.themovie.restapi.ApiInterface
import io.reactivex.disposables.CompositeDisposable

class UpComingViewModel(apiInterface: ApiInterface): ViewModel() {
    private val upcomingLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()
    private val upcomingSourceFactory = UpcomingDataSourceFactory(viewModelScope, apiInterface)

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()
        upcomingLiveData = LivePagedListBuilder(upcomingSourceFactory, pageConfig).build()
    }

    fun getUpcomingData(): MediatorLiveData<PagedList<Movies>> {
        uiList.addSource(upcomingLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(upcomingLiveData)
    }

    fun getLoadState(): LiveData<LoadDataState> {
        return Transformations.switchMap(upcomingSourceFactory.getUpcomingDataSource(), UpcomingDataSource::loadState)
    }

    fun retry(){
        upcomingSourceFactory.getUpcomingDataSource().value?.retry()
    }

    fun refresh(){
        upcomingSourceFactory.getUpcomingDataSource().value?.invalidate()
    }
}