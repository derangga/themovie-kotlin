package com.themovie.ui.search

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.repos.search.SearchMovieDataSourceBase
import com.themovie.repos.search.SearchMovieSourceFactory
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.Result
import javax.inject.Inject

class SearchMoviesViewModel @Inject constructor (
    apiInterface: ApiInterface
): ViewModel() {

    private val searchLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()
    private val searchSourceFactory =
        SearchMovieSourceFactory(
            viewModelScope,
            apiInterface,
            query
        )

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

    fun stopSubscribing(){
        uiList.removeSource(searchLiveData)
    }

    fun getLoadState(): LiveData<Result.Status> {
        return Transformations.switchMap<SearchMovieDataSourceBase, Result.Status>(
            searchSourceFactory.getResultSearch(),
            SearchMovieDataSourceBase::loadState
        )
    }

    fun retry(){
        searchSourceFactory.getResultSearch().value?.retry()
    }

    fun refresh(){
        searchSourceFactory.getResultSearch().value?.invalidate()
    }
}