package com.themovie.ui.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Upcoming
import com.themovie.repos.fromapi.discover.UpcomingDataSourceBase
import com.themovie.repos.fromapi.discover.UpcomingDataSourceFactory
import com.themovie.restapi.ApiInterface

class UpComingViewModel(apiInterface: ApiInterface): ViewModel() {
    private val upcomingLiveData: LiveData<PagedList<Upcoming>>
    private val uiList = MediatorLiveData<PagedList<Upcoming>>()
    private val upcomingSourceFactory = UpcomingDataSourceFactory(viewModelScope, apiInterface)

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()
        upcomingLiveData = LivePagedListBuilder(upcomingSourceFactory, pageConfig).build()
    }

    fun getUpcomingData(): MediatorLiveData<PagedList<Upcoming>> {
        uiList.addSource(upcomingLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(upcomingLiveData)
    }

    fun getLoadState(): LiveData<LoadDataState> {
        return Transformations.switchMap(upcomingSourceFactory.getUpcomingDataSource(), UpcomingDataSourceBase::loadState)
    }

    fun retry(){
        upcomingSourceFactory.getUpcomingDataSource().value?.retry()
    }

    fun refresh(){
        upcomingSourceFactory.getUpcomingDataSource().value?.invalidate()
    }
}