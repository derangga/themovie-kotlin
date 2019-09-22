package com.themovie.ui.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.repos.fromapi.discover.MovieDataSource
import com.themovie.repos.fromapi.discover.MovieDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel : ViewModel() {
    private var movieLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()
    private val composite: CompositeDisposable = CompositeDisposable()
    private val moviesSourceFactory: MovieDataSourceFactory

    init {
        moviesSourceFactory = MovieDataSourceFactory(composite)
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()

        movieLiveData = LivePagedListBuilder(moviesSourceFactory, pageConfig).build()

    }

    fun getMovieLiveData(): MediatorLiveData<PagedList<Movies>>{
        uiList.addSource(movieLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(movieLiveData)
    }


    fun getLoadState(): LiveData<LoadDataState> {
        return Transformations.switchMap<MovieDataSource, LoadDataState>(moviesSourceFactory.getMovieDataSource(), MovieDataSource::loadState)
    }

    fun retry(){
        moviesSourceFactory.getMovieDataSource().value?.retry()
    }

    fun refresh(){
        moviesSourceFactory.getMovieDataSource().value?.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        composite.dispose()
    }
}