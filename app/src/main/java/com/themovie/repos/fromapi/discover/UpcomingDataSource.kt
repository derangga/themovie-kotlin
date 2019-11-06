package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Upcoming
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpcomingDataSource
    (private val scope: CoroutineScope,
     private val apiInterface: ApiInterface): PageKeyedDataSource<Int, Upcoming>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retry: (() -> Any)? = null
    private var key = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Upcoming>) {
        updateState(LoadDataState.LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Upcoming>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Upcoming>) {

    }

    private fun fetchData(page: Int, callback: (List<Upcoming>?) -> Unit){
        scope.launch(getJobErrorHandler()) {
            val upcoming = apiInterface.getUpcomingMovies(ApiUrl.TOKEN, page)
            if(upcoming.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = upcoming.body()?.totalPages ?: 0
                callback(upcoming.body()?.results)
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