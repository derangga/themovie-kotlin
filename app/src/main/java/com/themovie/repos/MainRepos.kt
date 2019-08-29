package com.themovie.repos

import com.themovie.model.online.MainData
import com.themovie.model.online.TvResponse
import com.themovie.model.online.UpcomingResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainRepos() {

    fun getDataMovide(token: String): Observable<MainData>{
        val trendingData: Observable<TvResponse> = ApiClient.getApiBuilder().getTrendingTv(token).subscribeOn(Schedulers.io())
        val upcomingData: Observable<UpcomingResponse> = ApiClient.getApiBuilder().getUpcomingMovies(token, 1, "").subscribeOn(Schedulers.io())

        val call: Observable<MainData> = Observable.zip(trendingData, upcomingData,

            object: BiFunction<TvResponse, UpcomingResponse, MainData>{
                override fun apply(t1: TvResponse, t2: UpcomingResponse): MainData {
                    return MainData(t1, t2)
                }
            })
        return call
    }
}