package com.themovie.ui.search

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Tv
import com.themovie.repos.fromapi.search.SearchTvDataSourceBase
import com.themovie.repos.fromapi.search.SearchTvSourceFactory
import com.themovie.restapi.ApiInterface

class SearchTvViewModel(private val apiInterface: ApiInterface): ViewModel() {

    private val searchLiveData: LiveData<PagedList<Tv>>
    private val uiList = MediatorLiveData<PagedList<Tv>>()
    private val searchSourceFactory = SearchTvSourceFactory(viewModelScope, apiInterface, query)

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

    fun getSearchTvResult(): MutableLiveData<PagedList<Tv>>{
        uiList.addSource(searchLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(searchLiveData)
    }

    fun getLoadState(): LiveData<LoadDataState>{
        return Transformations.switchMap<SearchTvDataSourceBase, LoadDataState>(
                searchSourceFactory.getResultSearch(),
                SearchTvDataSourceBase::loadState
            )
    }

    fun retry(){
        searchSourceFactory.getResultSearch().value?.retry()
    }

    fun refresh(){
        searchSourceFactory.getResultSearch().value?.invalidate()
    }
}