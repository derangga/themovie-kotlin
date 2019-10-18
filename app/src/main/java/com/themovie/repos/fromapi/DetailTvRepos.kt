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
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import javax.inject.Inject

class DetailTvRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDetailData(token: String, tvId: Int): FetchDetailTvData? {
        var data: FetchDetailTvData? = null
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getTvDetail(tvId, token) }
                val cast = async(IO) { return@async apiInterface.getCreditsTv(tvId, token) }
                val recommendation = async(IO) { return@async apiInterface.getRecomendedTv(tvId, token, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { return@async apiInterface.getReviewsTV(tvId, token, Constant.LANGUAGE, 1) }

                if(detail.await().isSuccessful && cast.await().isSuccessful && recommendation.await().isSuccessful && reviews.await().isSuccessful){
                    data = FetchDetailTvData(
                        detail.await().body(),
                        cast.await().body(),
                        recommendation.await().body(),
                        reviews.await().body()
                    )
                }
            }
        }catch (e: Exception){
            throw e
        }

        return data
    }
}