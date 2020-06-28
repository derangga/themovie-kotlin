package com.themovie.repos.fromapi.search

import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BasePagingDataSource
import com.themovie.restapi.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchMovieDataSourceBase(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val query: String? =""
): BasePagingDataSource<Int, Movies>() {

    override fun loadFirstPage(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(Result.Status.LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        if(params.key <= pageSize){
            updateState(Result.Status.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun fetchData(page: Int, callback: (List<Movies>?) -> Unit) {
        scope.launch(IO + getJobErrorHandler()) {
            val response = apiInterface.getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, query.orEmpty(), page)
            if(response.isSuccessful){
                updateState(Result.Status.SUCCESS)
                pageSize = response.body()?.totalPages ?: 0
                callback(response.body()?.movies)
            } else updateState(Result.Status.ERROR)
        }
    }
}