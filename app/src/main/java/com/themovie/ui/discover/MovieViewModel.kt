package com.themovie.ui.discover

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themovie.model.db.Movies
import com.themovie.repos.discover.MovieDataSourceBase
import com.themovie.repos.discover.MovieDataSourceFactory
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.Result
import javax.inject.Inject

class MovieViewModel @Inject constructor (apiInterface: ApiInterface) : ViewModel() {

    private var movieLiveData: LiveData<PagedList<Movies>>
    private val uiList = MediatorLiveData<PagedList<Movies>>()
    private val moviesSourceFactory by lazy {
        MovieDataSourceFactory(
            viewModelScope,
            apiInterface
        )
    }

    init {
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


    fun getLoadState(): LiveData<Result.Status> {
        return Transformations.switchMap<MovieDataSourceBase, Result.Status>(
            moviesSourceFactory.getMovieDataSource(),
            MovieDataSourceBase::loadState
        )
    }

    fun retry(){
        moviesSourceFactory.getMovieDataSource().value?.retry()
    }

    fun refresh(){
        moviesSourceFactory.getMovieDataSource().value?.invalidate()
    }

    fun resetMovieWithGenre(genre: String){
        moviesSourceFactory.genre = genre
        moviesSourceFactory.getMovieDataSource().value?.invalidate()
    }
}