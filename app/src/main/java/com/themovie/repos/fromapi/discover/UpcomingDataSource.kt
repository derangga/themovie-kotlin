package com.themovie.repos.fromapi.discover

import com.themovie.helper.LoadDataState
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.PagingDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UpcomingDataSource(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface
): PagingDataSource<Int, Upcoming>() {

    override fun loadFirstPage(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Upcoming>) {
        updateState(LoadDataState.LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, Upcoming>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun fetchData(page: Int, callback: (List<Upcoming>?) -> Unit) {
        scope.launch(IO + getJobErrorHandler()) {
            val upcoming = apiInterface.getUpcomingMovies(ApiUrl.TOKEN, page)
            if(upcoming.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = upcoming.body()?.totalPages ?: 0
                callback(upcoming.body()?.results)
            } else updateState(LoadDataState.ERROR)
        }
    }
}