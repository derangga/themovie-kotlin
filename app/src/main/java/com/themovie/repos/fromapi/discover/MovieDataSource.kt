package com.themovie.repos.fromapi.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MovieDataSource(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface,
    private val genre: String
): PageKeyedDataSource<Int, Movies>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var key = 0
    private var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
        retry = {loadInitial(params, callback)}
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = {loadAfter(params, callback)}
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

    }

    private fun fetchData(page: Int, callback: (List<Movies>?) -> Unit){
        scope.launch(getJobErrorHandler()) {
            val discover = apiInterface.getDiscoverMovies(ApiUrl.TOKEN, Constant.LANGUAGE,
                Constant.SORTING, page, "2019", genre)
            if(discover.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = discover.body()?.totalPages ?: 0
                callback(discover.body()?.movies)
            } else updateState(LoadDataState.ERROR)
        }
    }

    private fun updateState(state: LoadDataState) {
        this.loadState.postValue(state)
    }

    fun retry(){
        val reCall = retry
        retry = null
        reCall?.invoke()
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        updateState(LoadDataState.ERROR)
    }
}