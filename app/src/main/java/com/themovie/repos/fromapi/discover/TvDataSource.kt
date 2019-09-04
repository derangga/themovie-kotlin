package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovertv.Tv
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.restapi.ApiClient
import com.themovie.restapi.ApiUrl
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class TvDataSource(private val composite: CompositeDisposable) : PageKeyedDataSource<Int, Tv>() {
    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tv>) {
        updateState(LoadDataState.LOADING)
        composite.add(
            ApiClient.getApiBuilder().getDiscoverTvs(
                ApiUrl.TOKEN, Constant.LANGUAGE,
                Constant.SORTING, 1, "")
                .subscribe(
                    object: Consumer<TvResponse> {
                        override fun accept(t: TvResponse?) {
                            updateState(LoadDataState.LOADED)
                            pageSize = t!!.totalPages
                            callback.onResult(t.results, null, 2)
                        }
                    }, object: Consumer<Throwable> {
                        override fun accept(t: Throwable?) {
                            updateState(LoadDataState.ERROR)
                            setRetry(Action { loadInitial(params, callback) })
                        }
                    })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {
        updateState(LoadDataState.LOADING)
        composite.add(
            ApiClient.getApiBuilder().getDiscoverTvs(
                ApiUrl.TOKEN, Constant.LANGUAGE,
                Constant.SORTING, params.key, "")
                .subscribe(object: Consumer<TvResponse> {
                    override fun accept(t: TvResponse?) {
                        updateState(LoadDataState.LOADED)
                        val key = params.key + 1
                        if(key < pageSize)
                            callback.onResult(t!!.results, key)
                    }
                }, object: Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        updateState(LoadDataState.ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tv>) {

    }

    private fun updateState(state: LoadDataState) {
        this.loadState.postValue(state)
    }

    fun retry(){
        if(retryCompletable != null){
            composite.add(
                retryCompletable!!.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if(action == null){
            null
        } else Completable.fromAction(action)
    }
}