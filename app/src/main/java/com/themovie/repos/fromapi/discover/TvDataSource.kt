package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovertv.Tv
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TvDataSource
    (private val scope: CoroutineScope,
     private val apiInterface: ApiInterface) : PageKeyedDataSource<Int, Tv>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tv>) {
        updateState(LoadDataState.LOADING)
        retry = { loadInitial(params, callback) }
        scope.launch {
            val discover = apiInterface.getDiscoverTvs(
                ApiUrl.TOKEN, Constant.LANGUAGE,
                Constant.SORTING, 1, "")
            if(discover.isSuccessful){
                updateState(LoadDataState.LOADED)
                pageSize = discover.body()!!.totalPages
                callback.onResult(discover.body()!!.results, null, 2)
            } else updateState(LoadDataState.ERROR)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            retry = { loadAfter(params, callback) }
            scope.launch {
                val discover = apiInterface.getDiscoverTvs(
                    ApiUrl.TOKEN, Constant.LANGUAGE,
                    Constant.SORTING, params.key, "")
                if(discover.isSuccessful){
                    updateState(LoadDataState.LOADED)
                    val key = params.key + 1
                    callback.onResult(discover.body()!!.results, key)
                } else updateState(LoadDataState.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {

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