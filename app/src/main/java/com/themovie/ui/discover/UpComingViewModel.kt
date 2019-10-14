package com.themovie.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.repos.fromapi.discover.UpcomingDataSource
import com.themovie.repos.fromapi.discover.UpcomingDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class UpComingViewModel(private val upcomingSourceFactory: UpcomingDataSourceFactory): ViewModel() {
    private val upcomingLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()

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

    override fun onCleared() {
        super.onCleared()
        upcomingSourceFactory.getUpcomingDataSource().value?.clearDisposable()
    }
}