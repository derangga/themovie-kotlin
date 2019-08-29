package com.themovie.restapi

import com.themovie.model.online.TvResponse
import com.themovie.model.online.UpcomingResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(ApiUrl.UPCOMING)
    fun getUpcomingMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ) : Observable<UpcomingResponse>

    @GET(ApiUrl.TRENDING)
    fun getTrendingTv(@Query("api_key") api_key: String): Observable<TvResponse>
}