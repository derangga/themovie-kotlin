package com.themovie.repos.fromapi.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Movies
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SearchMovieDataSource(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val query: String
): PageKeyedDataSource<Int, Movies>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var key = 0
    private var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
        retry = { loadInitial(params, callback) }

        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

    }

    private fun fetchData(page: Int, callback: (List<Movies>?) -> Unit){
        scope.launch(IO + getJobErrorHandler()) {
            val discover = apiInterface.getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, query, page)
            if(discover.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = discover.body()?.totalPages ?: 0
                callback(discover.body()?.movies)
            } else updateState(LoadDataState.ERROR)
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        updateState(LoadDataState.ERROR)
    }

    private fun updateState(state: LoadDataState) {
        this.loadState.postValue(state)
    }

    fun retry(){
        val reCall = retry
        retry = null
        reCall?.invoke()
    }
}