package com.aldebaran.data.network.source

import com.aldebaran.domain.Result.Status.*
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.repository.remote.BasePagingDataSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UpcomingDataSource(
    private val scope: CoroutineScope,
    private val remote: MovieRemoteSource
): BasePagingDataSource<Int, MovieResponse>() {

    override fun loadFirstPage(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, MovieResponse>) {
        updateState(LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it.toMutableList(), null, 2)
        }
    }

    override fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResponse>) {
        if(params.key <= pageSize){
            updateState(LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it.toMutableList(), key)
            }
        }
    }

    override fun fetchData(page: Int, callback: (List<MovieResponse>) -> Unit) {
        scope.launch(IO + getJobErrorHandler()) {
            val result = remote.getUpcomingMovie(page)
            when(result.status){
                SUCCESS -> {
                    updateState(SUCCESS)
                    pageSize = result.data?.totalPages ?: 0
                    callback(result.data?.results.orEmpty())
                }
                ERROR -> updateState(result.status)
                LOADING -> {}
            }
        }
    }
}