package com.themovie.repos.discover

import com.themovie.BuildConfig
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BasePagingDataSource
import com.themovie.restapi.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class UpcomingDataSourceBase(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface
): BasePagingDataSource<Int, Upcoming>() {

    override fun loadFirstPage(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Upcoming>) {
        updateState(Result.Status.LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, Upcoming>) {
        if(params.key <= pageSize){
            updateState(Result.Status.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun fetchData(page: Int, callback: (List<Upcoming>?) -> Unit) {
        scope.launch(IO + getJobErrorHandler()) {
            val upcoming = apiInterface.getUpcomingMovies(BuildConfig.TOKEN, page)
            if(upcoming.isSuccessful){
                updateState(Result.Status.SUCCESS)
                pageSize = upcoming.body()?.totalPages ?: 0
                callback(upcoming.body()?.results)
            } else updateState(Result.Status.ERROR)
        }
    }
}