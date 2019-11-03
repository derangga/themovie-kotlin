package com.themovie.repos.fromapi.discover

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themovie.helper.LoadDataState
import com.themovie.model.online.discovermv.Movies
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class SearchDataSource(private val composite: CompositeDisposable, private val movieName: String): PageKeyedDataSource<Int, Movies>() {

    val loadState: MutableLiveData<LoadDataState> = MutableLiveData()
    private var pageSize: Int = 0
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
//        composite.add(
//            ApiClient.getApiBuilder().getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, movieName, 1)
//                .subscribe(
//                    object: Consumer<MoviesResponse> {
//                    override fun accept(t: MoviesResponse?) {
//                        updateState(LoadDataState.LOADED)
//                        pageSize = t!!.totalPages
//                        callback.onResult(t.movies, null, 2)
//                    }
//                }, object: Consumer<Throwable>{
//                    override fun accept(t: Throwable?) {
//                        updateState(LoadDataState.ERROR)
//                        setRetry(Action { loadInitial(params, callback) })
//                    }
//                })
//        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        updateState(LoadDataState.LOADING)
//        composite.add(
//            ApiClient.getApiBuilder().getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, movieName, params.key)
//                .subscribe(
//                    object: Consumer<MoviesResponse>{
//                    override fun accept(t: MoviesResponse?) {
//                        updateState(LoadDataState.LOADED)
//                        val key = params.key + 1
//                        if(key < pageSize){
//                            callback.onResult(t!!.movies, key)
//                        }
//                    }
//                }, object: Consumer<Throwable>{
//                    override fun accept(t: Throwable?) {
//                        updateState(LoadDataState.ERROR)
//                        setRetry(Action { loadAfter(params, callback) })
//                    }
//                })
//        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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