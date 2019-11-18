package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.FetchPersonData
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ApiRepository
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDetailDataMovie(movieId: Int, callback: ApiCallback<FetchDetailMovieData>) {
        var data: FetchDetailMovieData?
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getMovieDetail(movieId, ApiUrl.TOKEN) }
                val cast = async(IO) { return@async apiInterface.getCredits(movieId, ApiUrl.TOKEN) }
                val recommendation = async(IO) { return@async apiInterface.getRecomendedMovies(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { return@async apiInterface.getReviews(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) { return@async apiInterface.getVideosMovie(movieId, ApiUrl.TOKEN, "") }

                if(detail.await().isSuccessful && cast.await().isSuccessful &&
                    videos.await().isSuccessful && recommendation.await().isSuccessful &&
                    reviews.await().isSuccessful){

                    data = FetchDetailMovieData(
                        detail.await().body(),
                        cast.await().body(),
                        videos.await().body(),
                        recommendation.await().body(),
                        reviews.await().body()
                    )

                    callback.onSuccessRequest(data)
                } else callback.onErrorRequest(detail.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }

    suspend fun getDetailDataTv(tvId: Int, callback: ApiCallback<FetchDetailTvData>) {
        var data: FetchDetailTvData?
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getTvDetail(tvId, ApiUrl.TOKEN) }
                val cast = async(IO) { return@async apiInterface.getCreditsTv(tvId, ApiUrl.TOKEN) }
                val recommendation = async(IO) { return@async apiInterface.getRecomendedTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { return@async apiInterface.getReviewsTV(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) { return@async apiInterface.getVideosTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE) }

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
                    callback.onSuccessRequest(data)
                } else callback.onErrorRequest(detail.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }

    suspend fun getDataMovie(): FetchMainData? {
        var data: FetchMainData? = null
        try {
            coroutineScope {
                val popular = async(IO) { return@async apiInterface.getPopularMovie(ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val genre = async(IO) { return@async apiInterface.getGenres(ApiUrl.TOKEN) }
                val upcoming = async(IO) { return@async apiInterface.getUpcomingMovies(ApiUrl.TOKEN, 1) }
                val discoverTv = async(IO) {
                    return@async apiInterface.getDiscoverTvs(ApiUrl.TOKEN, Constant.LANGUAGE, Constant.SORTING, 1, "US")
                }
                val discoverMv = async(IO) {
                    return@async apiInterface.getDiscoverMovies(ApiUrl.TOKEN, Constant.LANGUAGE, Constant.SORTING, 1,
                        "2019", "")
                }
                if(popular.await().isSuccessful && upcoming.await().isSuccessful &&
                    genre.await().isSuccessful && discoverTv.await().isSuccessful && discoverMv.await().isSuccessful){
                    data = FetchMainData(
                        popular.await().body(),
                        genre.await().body(),
                        upcoming.await().body(),
                        discoverTv.await().body(),
                        discoverMv.await().body()
                    )
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }

    suspend fun getPersonData(personId: Int, callback: ApiCallback<FetchPersonData>) {
        var data: FetchPersonData?
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getPerson(personId, ApiUrl.TOKEN) }
                val person = async(IO) { return@async apiInterface.getFilmography(personId, ApiUrl.TOKEN) }
                if(detail.await().isSuccessful && person.await().isSuccessful){
                    data = FetchPersonData(detail.await().body(), person.await().body())
                    callback.onSuccessRequest(data)
                } else callback.onErrorRequest(detail.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }
}