package com.themovie.ui.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Tv
import com.themovie.repos.discover.TvDataSourceBase
import com.themovie.repos.discover.TvDataSourceFactory
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.Result
import javax.inject.Inject

class TvViewModel @Inject constructor (apiInterface: ApiInterface): ViewModel() {

    private var tvLiveData: LiveData<PagedList<Tv>>
    private val uiList = MediatorLiveData<PagedList<Tv>>()
    private val tvSourceFactory by lazy {
        TvDataSourceFactory(
            viewModelScope,
            apiInterface
        )
    }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()

        tvLiveData = LivePagedListBuilder(tvSourceFactory, pageConfig).build()
    }

    fun getTvLiveData(): MediatorLiveData<PagedList<Tv>> {
        uiList.addSource(tvLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(tvLiveData)
    }


    fun getLoadState(): LiveData<Result.Status> {
        return Transformations
            .switchMap<TvDataSourceBase, Result.Status>(tvSourceFactory.getTvDataSource(), TvDataSourceBase::loadState)
    }

    fun retry(){
        tvSourceFactory.getTvDataSource().value?.retry()
    }

    fun refresh(){
        tvSourceFactory.getTvDataSource().value?.invalidate()
    }
}