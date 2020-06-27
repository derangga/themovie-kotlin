package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.FetchPersonData
import com.themovie.repos.local.LocalRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import com.themovie.restapi.BaseRemoteDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Deprecated("")
class ApiRepository
@Inject constructor(
    private val apiInterface: ApiInterface
) : BaseRemoteDataSource() {

    suspend fun getDetailDataMovie(movieId: Int, callback: ApiCallback<FetchDetailMovieData>) {
        var data: FetchDetailMovieData?
        try {
            coroutineScope {
                val detail = async(IO) { apiInterface.getMovieDetail(movieId, ApiUrl.TOKEN) }
                val cast = async(IO) { apiInterface.getCredits(movieId, ApiUrl.TOKEN) }
                val recommendation = async(IO) { apiInterface.getRecomendedMovies(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { apiInterface.getReviews(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) { apiInterface.getVideosMovie(movieId, ApiUrl.TOKEN, "") }

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
                val detail = async(IO) { apiInterface.getTvDetail(tvId, ApiUrl.TOKEN) }
                val cast = async(IO) { apiInterface.getCreditsTv(tvId, ApiUrl.TOKEN) }
                val recommendation = async(IO) { apiInterface.getRecomendedTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) { apiInterface.getReviewsTV(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) { apiInterface.getVideosTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE) }

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

    suspend fun getPersonData(personId: Int, callback: ApiCallback<FetchPersonData>) {
        var data: FetchPersonData?
        try {
            coroutineScope {
                val detail = async(IO) { apiInterface.getPerson(personId, ApiUrl.TOKEN) }
                val person = async(IO) { apiInterface.getFilmography(personId, ApiUrl.TOKEN) }
                val personPhoto = async(IO) { apiInterface.getPersonImages(personId, ApiUrl.TOKEN) }
                if(detail.await().isSuccessful &&
                    person.await().isSuccessful &&
                        personPhoto.await().isSuccessful){
                    data = FetchPersonData(detail.await().body(), person.await().body(), personPhoto.await().body())
                    callback.onSuccessRequest(data)
                } else callback.onErrorRequest(detail.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }

    suspend fun getSuggestionMoviesSearch(query: String) = getResult {
        apiInterface.getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
    }

    suspend fun getSuggestionTvSearch(query: String) = getResult {
        apiInterface.getSearchTv(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
    }
}