package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DetailMovieRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDetailData(token: String, movieId: Int): FetchDetailMovieData? {
        var data: FetchDetailMovieData? = null
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getMovieDetail(movieId, token) }
                val cast = async(IO) { return@async apiInterface.getCredits(movieId, token) }
                val recommendation = async(IO) { return@async apiInterface.getRecomendedMovies(movieId, token, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { return@async apiInterface.getReviews(movieId, token, Constant.LANGUAGE, 1) }

                if(detail.await().isSuccessful && cast.await().isSuccessful && recommendation.await().isSuccessful && reviews.await().isSuccessful){
                    data = FetchDetailMovieData(
                        detail.await().body(),
                        cast.await().body(),
                        recommendation.await().body(),
                        reviews.await().body()
                    )
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }
}