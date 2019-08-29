package com.themovie.restapi

import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.discovermv.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiUrl.UPCOMING)
    fun getUpcomingMovies (
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ) : Observable<UpcomingResponse>

    @GET(ApiUrl.TRENDING)
    fun getTrendingTv(@Query("api_key") api_key: String): Observable<TvResponse>

    @GET("discover/movie")
    fun getDiscoverMovies (
        @Query("api_key") api_key: String ,
        @Query("language") language: String ,
        @Query("sort_by") sort_by: String ,
        @Query("page") page: Int ,
        @Query("primary_release_year") primary_release_year: String ,
        @Query("with_genres") with_genres: String
    ) : Observable<MoviesResponse>

    @GET("discover/tv")
    fun getDiscoverTvs(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("page") page: Int,
        @Query("timezone") timezone: String
    ) : Observable<TvResponse>
}