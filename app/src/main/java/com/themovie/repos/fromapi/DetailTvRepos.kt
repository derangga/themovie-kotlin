package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailTvData
import com.themovie.restapi.ApiInterface
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
                val videos = async(IO) { return@async apiInterface.getVideosTv(tvId, token, Constant.LANGUAGE) }

                if(detail.await().isSuccessful && cast.await().isSuccessful &&
                    videos.await().isSuccessful && recommendation.await().isSuccessful &&
                    reviews.await().isSuccessful){

                    data = FetchDetailTvData(
                        detail.await().body(),
                        cast.await().body(),
                        videos.await().body(),
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