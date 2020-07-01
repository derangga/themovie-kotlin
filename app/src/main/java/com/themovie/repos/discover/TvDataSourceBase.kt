package com.themovie.repos.discover

import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.db.Tv
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BasePagingDataSource
import com.themovie.restapi.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TvDataSourceBase(
    private val scope: CoroutineScope,
    private val apiInterface: ApiInterface
) : BasePagingDataSource<Int, Tv>() {

    override fun loadFirstPage(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tv>) {
        updateState(Result.Status.LOADING)
        retry = { loadInitial(params, callback) }
        fetchData(1){
            callback.onResult(it!!.toMutableList(), null, 2)
        }
    }

    override fun loadNextPage(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {
        if(params.key <= pageSize){
            updateState(Result.Status.LOADING)
            retry = { loadAfter(params, callback) }
            fetchData(params.key){
                key = params.key + 1
                callback.onResult(it!!.toMutableList(), key)
            }
        }
    }

    override fun fetchData(page: Int, callback: (List<Tv>?) -> Unit) {
        scope.launch(IO + getJobErrorHandler()) {
            val discover = apiInterface.getDiscoverTvs(
                ApiUrl.TOKEN, Constant.LANGUAGE,
                Constant.SORTING, page, "")
            if(discover.isSuccessful){
                updateState(Result.Status.SUCCESS)
                pageSize = discover.body()?.totalPages ?: 0
                callback(discover.body()?.results)
            } else updateState(Result.Status.ERROR)
        }
    }
}