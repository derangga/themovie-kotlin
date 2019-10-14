package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.restapi.ApiClient
import com.themovie.restapi.ApiInterface
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailTvRepos
@Inject constructor(private val apiInterface: ApiInterface){

    fun getDetailData(token: String, tvId: Int): Observable<FetchDetailTvData> {
        val detailTv = apiInterface.getTvDetail(tvId, token).subscribeOn(Schedulers.io())
        val castMovie = apiInterface.getCreditsTv(tvId, token).subscribeOn(Schedulers.io())
        val recommended = apiInterface.getRecomendedTv(tvId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())
        val reviews = apiInterface.getReviewsTV(tvId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())

        val call: Observable<FetchDetailTvData> = Observable.zip(detailTv, castMovie, recommended, reviews,
            Function4<DetailTvResponse, CastResponse, TvResponse, ReviewResponse, FetchDetailTvData>
            { t1, t2, t3, t4 -> FetchDetailTvData(t1, t2, t3, t4) })

        return call
    }
}