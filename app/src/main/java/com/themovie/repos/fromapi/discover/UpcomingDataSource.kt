package com.themovie.repos.fromapi.discover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.restapi.ApiClient
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpcomingDataSource
    @Inject constructor(private val apiInterface: ApiInterface): PageKeyedDataSource<Int, Movies>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retryCompletable: Completable? = null
    private val composite = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
        composite.add(
            apiInterface.getUpcomingMovies(ApiUrl.TOKEN, 1, "")
                .subscribe(
                    object: Consumer<UpcomingResponse> {
                        override fun accept(t: UpcomingResponse?) {
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        if(params.key <= pageSize){
            updateState(LoadDataState.LOADING)
            composite.add(
                apiInterface.getUpcomingMovies(ApiUrl.TOKEN, params.key, "")
                    .subscribe(object: Consumer<UpcomingResponse> {
                        override fun accept(t: UpcomingResponse?) {
                            updateState(LoadDataState.LOADED)
                            val key = params.key + 1
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
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

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

    fun clearDisposable(){
        composite.clear()
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if(action == null){
            null
        } else Completable.fromAction(action)
    }
}