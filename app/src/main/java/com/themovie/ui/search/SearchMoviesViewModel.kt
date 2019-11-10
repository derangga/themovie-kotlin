package com.themovie.ui.search

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.model.db.Movies
import com.themovie.repos.fromapi.search.SearchMovieSourceFactory
import com.themovie.restapi.ApiInterface

class SearchMoviesViewModel(apiInterface: ApiInterface): ViewModel() {

    private val searchLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()
    private val searchSourceFactory = SearchMovieSourceFactory(viewModelScope, apiInterface, query)

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


    fun getSearchMovies(): MutableLiveData<PagedList<Movies>>{
        uiList.addSource(searchLiveData){
            uiList.value = it
        }
        return uiList
    }
}