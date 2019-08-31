package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailData
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class DetailRepos {

    fun getDetailData(token: String, movieId: Int): Observable<FetchDetailData> {
        val detailMovie = ApiClient.getApiBuilder().getMovieDetail(movieId, token).subscribeOn(Schedulers.io())
        val castMovie = ApiClient.getApiBuilder().getCredits(movieId, token).subscribeOn(Schedulers.io())
        val recommended = ApiClient.getApiBuilder().getRecomendedMovies(movieId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())
        val reviews = ApiClient.getApiBuilder().getReviews(movieId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())

        val call: Observable<FetchDetailData> = Observable.zip(detailMovie, castMovie, recommended, reviews,
            object: Function4<DetailMovieResponse, CastResponse, MoviesResponse, ReviewResponse, FetchDetailData> {
                override fun apply(
                    t1: DetailMovieResponse,
                    t2: CastResponse,
                    t3: MoviesResponse,
                    t4: ReviewResponse
                ): FetchDetailData {
                    return FetchDetailData(t1, t2, t3, t4)
                }
            })

        return call
    }
}