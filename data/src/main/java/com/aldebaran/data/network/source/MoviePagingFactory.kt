package com.aldebaran.data.network.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import kotlinx.coroutines.CoroutineScope

class MoviePagingFactory(
    private val scope: CoroutineScope,
    private val remote: MovieRemoteSource
): DataSource.Factory<Int, MovieResponse>() {

    private val movieDataSourceLiveData = MutableLiveData<MoviePagingSource>()
    var genre: String = ""

    override fun create(): DataSource<Int, MovieResponse> {
        val dataSource = MoviePagingSource(
            scope,
            remote,
            genre
        )
        movieDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getMovieDataSource(): MutableLiveData<MoviePagingSource> {
        return movieDataSourceLiveData
    }
}