package com.themovie.restapi

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.LoadDataState
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BasePagingDataSource<V, T>: PageKeyedDataSource<V, T>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    protected var pageSize: Int = 0
    protected var key = 0
    protected var retry: (() -> Any)? = null

    protected abstract fun loadFirstPage(params: LoadInitialParams<V>, callback: LoadInitialCallback<V, T>)
    protected abstract fun loadNextPage(params: LoadParams<V>, callback: LoadCallback<V, T>)

    override fun loadInitial(params: LoadInitialParams<V>, callback: LoadInitialCallback<V, T>) {
        loadFirstPage(params, callback)
    }

    override fun loadAfter(params: LoadParams<V>, callback: LoadCallback<V, T>) {
        loadNextPage(params, callback)
    }

    override fun loadBefore(params: LoadParams<V>, callback: LoadCallback<V, T>) {

    }

    protected fun updateState(state: LoadDataState) {
        this.loadState.postValue(state)
    }

    fun retry(){
        val reCall = retry
        retry = null
        reCall?.invoke()
    }

    protected fun getJobErrorHandler() = CoroutineExceptionHandler { _, _ ->
        updateState(LoadDataState.ERROR)
    }

    protected abstract fun fetchData(page: Int, callback: (List<T>?) -> Unit)
}
