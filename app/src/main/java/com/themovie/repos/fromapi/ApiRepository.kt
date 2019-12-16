package com.themovie.repos.fromapi

import android.util.Log
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.db.*
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.repos.local.LocalRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepository
@Inject constructor(
    private val apiInterface: ApiInterface,
    private val localRepos: LocalRepository
){

    suspend fun getMainData(callback: ApiCallback<LoadDataState>){
        try {
            coroutineScope {
                val trendingRows = async(IO) { localRepos.getTrendingRows() }
                val upcomingRows = async(IO) { localRepos.getUpcomingRows() }
                val genreRows = async(IO) { localRepos.getGenreRows() }
                val tvRows = async(IO) { localRepos.getTvRows() }
                val movieRows = async(IO) { localRepos.getMoviesRows() }
                val popular = async(IO) { apiInterface.getPopularMovie(ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val genre = async(IO) { apiInterface.getGenres(ApiUrl.TOKEN) }
                val upcoming = async(IO) { apiInterface.getUpcomingMovies(ApiUrl.TOKEN, 1) }
                val discoverTv = async(IO) {
                    apiInterface.getDiscoverTvs(ApiUrl.TOKEN, Constant.LANGUAGE, Constant.SORTING, 1, "US")
                }
                val discoverMv = async(IO) {
                    apiInterface.getDiscoverMovies(ApiUrl.TOKEN, Constant.LANGUAGE, Constant.SORTING, 1,
                        "2019", "")
                }
                if(popular.await().isSuccessful && upcoming.await().isSuccessful &&
                    genre.await().isSuccessful && discoverTv.await().isSuccessful && discoverMv.await().isSuccessful){

                    withContext(IO){
                        if(trendingRows.await() == 0 && upcomingRows.await() == 0 &&
                            genreRows.await() == 0 && tvRows.await() == 0 &&
                            movieRows.await() == 0){
                            localRepos.insertAllTrend(popular.await().body()?.results!!)
                            localRepos.insertAllUpcoming(upcoming.await().body()?.results!!)
                            localRepos.insertAllGenre(genre.await().body()?.genres!!)
                            localRepos.insertAllTv(discoverTv.await().body()?.results!!)
                            localRepos.insertAllMovie(discoverMv.await().body()?.movies!!)

                        }

                        else {
                            updateLocalData(popular.await().body()?.results!!, upcoming.await().body()?.results!!,
                                genre.await().body()?.genres!!, discoverTv.await().body()?.results!!,
                                discoverMv.await().body()?.movies!!)
                        }
                    }
                    callback.onSuccessRequest(LoadDataState.LOADED)
                } else callback.onErrorRequest(null)
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }

    private suspend fun updateLocalData(trendingList: List<Trending>, upcomingList: List<Upcoming>,
                                genreList: List<Genre>, tvList: List<Tv>, movieList: List<Movies>)
    {
        trendingList.forEachIndexed { index, trending ->
            val data = Trending(
                index + 1, trending.id, trending.title,
                trending.posterPath, trending.backdropPath,
                trending.overview, trending.voteAverage, trending.releaseDate
            )
            localRepos.update(data)
        }

        upcomingList.forEachIndexed { index, upcoming ->
            val data = Upcoming(
                index + 1, upcoming.id, upcoming.title,
                upcoming.posterPath, upcoming.backdropPath,
                upcoming.overview, upcoming.voteAverage, upcoming.releaseDate
            )
            localRepos.update(data)
        }

        genreList.forEachIndexed { index, genre ->
            val data = Genre(index + 1, genre.id, genre.name)
            localRepos.update(data)
        }

        tvList.forEachIndexed { index, tv ->
            val data = Tv(
                index + 1, tv.id, tv.name,
                tv.voteAverage, tv.voteCount,
                tv.posterPath, tv.backdropPath, tv.overview
            )
            localRepos.update(data)
        }

        movieList.forEachIndexed { index, movies ->
            val data = Movies(
                index + 1, movies.id,
                movies.title, movies.posterPath, movies.backdropPath,
                movies.overview, movies.voteAverage, movies.releaseDate
            )
            localRepos.update(data)
        }
    }

    suspend fun getDetailDataMovie(movieId: Int, callback: ApiCallback<FetchDetailMovieData>) {
        var data: FetchDetailMovieData?
        try {
            coroutineScope {
                val detail = async(IO) {  apiInterface.getMovieDetail(movieId, ApiUrl.TOKEN) }
                val cast = async(IO) {  apiInterface.getCredits(movieId, ApiUrl.TOKEN) }
                val recommendation = async(IO) {  apiInterface.getRecomendedMovies(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) {  apiInterface.getReviews(movieId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) {  apiInterface.getVideosMovie(movieId, ApiUrl.TOKEN, "") }

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
                val detail = async(IO) {  apiInterface.getTvDetail(tvId, ApiUrl.TOKEN) }
                val cast = async(IO) {  apiInterface.getCreditsTv(tvId, ApiUrl.TOKEN) }
                val recommendation = async(IO) {  apiInterface.getRecomendedTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val reviews = async(IO) {  apiInterface.getReviewsTV(tvId, ApiUrl.TOKEN, Constant.LANGUAGE, 1) }
                val videos = async(IO) {  apiInterface.getVideosTv(tvId, ApiUrl.TOKEN, Constant.LANGUAGE) }

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
                val detail = async(IO) {  apiInterface.getPerson(personId, ApiUrl.TOKEN) }
                val person = async(IO) {  apiInterface.getFilmography(personId, ApiUrl.TOKEN) }
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

    suspend fun getSuggestionMoviesSearch(query: String, callback: ApiCallback<MoviesResponse>){
        try {
            coroutineScope {
                val response = async(IO){
                     apiInterface.getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
                }
                if(response.await().isSuccessful) callback.onSuccessRequest(response.await().body())
                else callback.onErrorRequest(response.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }

    suspend fun getSuggestionTvSearch(query: String, callback: ApiCallback<TvResponse>){
        try {
            coroutineScope{
                val response = async(IO) {
                     apiInterface.getSearchTv(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
                }
                if(response.await().isSuccessful) callback.onSuccessRequest(response.await().body())
                else callback.onErrorRequest(response.await().errorBody())
            }
        } catch (e: Exception){
            callback.onFailure(e)
        }
    }
}