package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.restapi.ApiClient
import com.themovie.restapi.ApiInterface
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailMovieRepos
@Inject constructor(private val apiInterface: ApiInterface){

    fun getDetailData(token: String, movieId: Int): Observable<FetchDetailMovieData> {
        val detailMovie = apiInterface.getMovieDetail(movieId, token).subscribeOn(Schedulers.io())
        val castMovie = apiInterface.getCredits(movieId, token).subscribeOn(Schedulers.io())
        val recommended = apiInterface.getRecomendedMovies(movieId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())
        val reviews = apiInterface.getReviews(movieId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())

        val call: Observable<FetchDetailMovieData> = Observable.zip(detailMovie, castMovie, recommended, reviews,
            Function4<DetailMovieResponse, CastResponse, MoviesResponse, ReviewResponse, FetchDetailMovieData>
            { t1, t2, t3, t4 -> FetchDetailMovieData(t1, t2, t3, t4) })

        return call

    }
}