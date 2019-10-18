package com.themovie.repos.fromapi.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpcomingDataSource
    (private val scope: CoroutineScope,
     private val apiInterface: ApiInterface): PageKeyedDataSource<Int, Movies>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
        retry = { loadInitial(params, callback) }
        scope.launch {
            val upcoming = apiInterface.getUpcomingMovies(ApiUrl.TOKEN, 1, "")
            if(upcoming.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = upcoming.body()!!.totalPages
                callback.onResult(upcoming.body()!!.results, null, 2)
            } else updateState(LoadDataState.ERROR)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = { loadAfter(params, callback) }
            scope.launch {
                val upcoming = apiInterface.getUpcomingMovies(ApiUrl.TOKEN, params.key, "")
                if(upcoming.isSuccessful){
                    updateState(LoadDataState.LOADED)
                    val key = params.key + 1
                    callback.onResult(upcoming.body()!!.results, key)
                } else updateState(LoadDataState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

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