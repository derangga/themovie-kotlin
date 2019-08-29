package com.themovie.repos

import com.themovie.helper.Constant
import com.themovie.model.online.MainData
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class MainRepos {

    fun getDataMovide(token: String): Observable<MainData>{
        val trendingData: Observable<TvResponse> = ApiClient.getApiBuilder().getTrendingTv(token).subscribeOn(Schedulers.io())
        val upcomingData: Observable<UpcomingResponse> = ApiClient.getApiBuilder().getUpcomingMovies(token, 1, "").subscribeOn(Schedulers.io())
        val discoverTv: Observable<TvResponse> = ApiClient.getApiBuilder().getDiscoverTvs(token, Constant.LANGUAGE, Constant.SORTING, 1, "US").subscribeOn(Schedulers.io())
        val discoverMv: Observable<MoviesResponse> = ApiClient.getApiBuilder().getDiscoverMovies(token, Constant.LANGUAGE, Constant.SORTING, 1, "2019", "").subscribeOn(Schedulers.io())

        val call: Observable<MainData> = Observable.zip(trendingData, upcomingData, discoverTv, discoverMv,
            object: Function4<TvResponse, UpcomingResponse, TvResponse, MoviesResponse, MainData>{
                override fun apply(trending: TvResponse, upcoming: UpcomingResponse, discoverTv: TvResponse, discoverMovies: MoviesResponse): MainData {
                    return MainData(trending, upcoming, discoverTv, discoverMovies)
                }
            })
        return call
    }
}