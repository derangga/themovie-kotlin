package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.FetchPersonData
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ApiRepository
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDetailDataMovie(token: String, movieId: Int): FetchDetailMovieData? {
        var data: FetchDetailMovieData? = null
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getMovieDetail(movieId, token) }
                val cast = async(IO) { return@async apiInterface.getCredits(movieId, token) }
                val recommendation = async(IO) { return@async apiInterface.getRecomendedMovies(movieId, token, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { return@async apiInterface.getReviews(movieId, token, Constant.LANGUAGE, 1) }
                val videos = async(IO) { return@async apiInterface.getVideosMovie(movieId, token, "") }

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
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }

    suspend fun getDetailDataTv(token: String, tvId: Int): FetchDetailTvData? {
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
        } catch (e: java.lang.Exception){
            throw e
        }
        return data
    }

    suspend fun getDataMovie(token: String): FetchMainData? {
        var data: FetchMainData? = null
        try {
            coroutineScope {
                val popular = async(IO) { return@async apiInterface.getPopularMovie(token, Constant.LANGUAGE, 1) }
                val genre = async(IO) { return@async apiInterface.getGenres(token) }
                val upcoming = async(IO) { return@async apiInterface.getUpcomingMovies(token, 1) }
                val discoverTv = async(IO) {
                    return@async apiInterface.getDiscoverTvs(token, Constant.LANGUAGE, Constant.SORTING, 1, "US")
                }
                val discoverMv = async(IO) {
                    return@async apiInterface.getDiscoverMovies(token, Constant.LANGUAGE, Constant.SORTING, 1,
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

    suspend fun getPersonData(token: String, personId: Int): FetchPersonData? {
        var data: FetchPersonData? = null
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getPerson(personId, token) }
                val person = async(IO) { return@async apiInterface.getFilmography(personId, token) }
                if(detail.await().isSuccessful && person.await().isSuccessful){
                    data = FetchPersonData(detail.await().body(), person.await().body())
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }
}