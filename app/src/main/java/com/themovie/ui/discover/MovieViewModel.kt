package com.themovie.ui.discover

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.aldebaran.data.network.source.MoviePagingFactory
import com.aldebaran.data.network.source.MoviePagingSource
import com.aldebaran.domain.Result.*
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class MovieViewModel @ViewModelInject constructor (remote: MovieRemoteSource) : ViewModel() {

    private var movieLiveData: LiveData<PagedList<MovieResponse>>
    private val uiList = MediatorLiveData<PagedList<MovieResponse>>()
    private val moviesSourceFactory by lazy {
        MoviePagingFactory(
            viewModelScope,
            remote
        )
    }

    init {
        val pageConfig = PagedList.Config.Builder()
            .setPageSize(2)
            .setEnablePlaceholders(false)
            .build()

        movieLiveData = LivePagedListBuilder(moviesSourceFactory, pageConfig).build()

    }

    fun getMovieLiveData(): MediatorLiveData<PagedList<MovieResponse>>{
        uiList.addSource(movieLiveData){
            uiList.value = it
        }
        return uiList
    }

    fun stopSubscribing(){
        uiList.removeSource(movieLiveData)
    }


    fun getLoadState(): LiveData<Status> {
        return Transformations.switchMap(
            moviesSourceFactory.getMovieDataSource(),
            MoviePagingSource::loadState
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