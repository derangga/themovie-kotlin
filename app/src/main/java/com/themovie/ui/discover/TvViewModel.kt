package com.themovie.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovertv.Tv
import com.themovie.repos.fromapi.discover.TvDataSource
import com.themovie.repos.fromapi.discover.TvDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class TvViewModel(private val tvSourceFactory: TvDataSourceFactory): ViewModel() {

    private var tvLiveData: LiveData<PagedList<Tv>>
    private val uiList = MediatorLiveData<PagedList<Tv>>()


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


    fun getLoadState(): LiveData<LoadDataState> {
        return Transformations.switchMap<TvDataSource, LoadDataState>(tvSourceFactory.getTvDataSource(), TvDataSource::loadState)
    }

    fun retry(){
        tvSourceFactory.getTvDataSource().value?.retry()
    }

    fun refresh(){
        tvSourceFactory.getTvDataSource().value?.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        tvSourceFactory.getTvDataSource().value?.clearComposite()
    }
}