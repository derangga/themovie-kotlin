package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers

class DetailTvRepos {

    fun getDetailData(token: String, tvId: Int): Observable<FetchDetailTvData> {
        val detailTv = ApiClient.getApiBuilder().getTvDetail(tvId, token).subscribeOn(Schedulers.io())
        val castMovie = ApiClient.getApiBuilder().getCreditsTv(tvId, token).subscribeOn(Schedulers.io())
        val recommended = ApiClient.getApiBuilder().getRecomendedTv(tvId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())
        val reviews = ApiClient.getApiBuilder().getReviewsTV(tvId, token, Constant.LANGUAGE, 1).subscribeOn(Schedulers.io())

        val call: Observable<FetchDetailTvData> = Observable.zip(detailTv, castMovie, recommended, reviews,
            Function4<DetailTvResponse, CastResponse, TvResponse, ReviewResponse, FetchDetailTvData>
            { t1, t2, t3, t4 -> FetchDetailTvData(t1, t2, t3, t4) })

        return call
    }
}