package com.themovie.restapi

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.discovermv.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET(ApiUrl.DISCOVER_MOVIES)
    fun getDiscoverMovies (
        @Query("api_key") api_key: String ,
        @Query("language") language: String ,
        @Query("sort_by") sort_by: String ,
        @Query("page") page: Int ,
        @Query("primary_release_year") primary_release_year: String ,
        @Query("with_genres") with_genres: String
    ) : Observable<MoviesResponse>

    @GET(ApiUrl.DISCOVER_TV)
    fun getDiscoverTvs(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("page") page: Int,
        @Query("timezone") timezone: String
    ) : Observable<TvResponse>

    @GET(ApiUrl.DETAIL_MOVIE)
    fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<DetailMovieResponse>

    @GET(ApiUrl.RECOMMENDATION)
    fun getRecomendedMovies(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<MoviesResponse>

    @GET(ApiUrl.REVIEWS)
    fun getReviews(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<ReviewResponse>

    @GET(ApiUrl.CREDITS)
    fun getCredits(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<CastResponse>
}