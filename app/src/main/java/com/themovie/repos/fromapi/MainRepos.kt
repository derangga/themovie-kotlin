package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.restapi.ApiInterface
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainRepos
@Inject constructor(private val apiInterface: ApiInterface){

    fun getDataMovie(token: String): Observable<FetchMainData>{
        val trendingData: Observable<TvResponse> = apiInterface.getTrendingTv(token).subscribeOn(Schedulers.io())
        val upcomingData: Observable<UpcomingResponse> = apiInterface.getUpcomingMovies(token, 1, "").subscribeOn(Schedulers.io())
        val discoverTv: Observable<TvResponse> = apiInterface.getDiscoverTvs(token, Constant.LANGUAGE, Constant.SORTING, 1, "US").subscribeOn(Schedulers.io())
        val discoverMv: Observable<MoviesResponse> = apiInterface.getDiscoverMovies(token, Constant.LANGUAGE, Constant.SORTING, 1, "2019", "").subscribeOn(Schedulers.io())

        val call: Observable<FetchMainData> = Observable.zip(trendingData, upcomingData, discoverTv, discoverMv,
            object: Function4<TvResponse, UpcomingResponse, TvResponse, MoviesResponse, FetchMainData>{
                override fun apply(trending: TvResponse, upcoming: UpcomingResponse, discoverTv: TvResponse, discoverMovies: MoviesResponse): FetchMainData {
                    return FetchMainData(trending, upcoming, discoverTv, discoverMovies)
                }
            })
        return call
    }

//    fun getTrending(token: String) : Observable<TvResponse> {
//        return apiInterface.getTrendingTv(token)
//    }
//
//    fun getUpcomingData(token: String): Observable<UpcomingResponse> {
//        return apiInterface.getUpcomingMovies(token, 1, "")
//    }
//
//    fun getDiscoverTv(token: String): Observable<TvResponse> {
//        return apiInterface.getDiscoverTvs(token, Constant.LANGUAGE, Constant.SORTING, 1, "US")
//    }
//
//    fun getDiscoverMv(token: String): Observable<MoviesResponse> {
//        return apiInterface.getDiscoverMovies(token, Constant.LANGUAGE, Constant.SORTING, 1, "2019", "")
//    }
}