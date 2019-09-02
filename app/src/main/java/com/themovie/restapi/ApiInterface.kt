package com.themovie.restapi

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.model.online.video.VideoResponse
import io.reactivex.Observable
import io.reactivex.Single
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

    @GET(ApiUrl.DETAIL_TV)
    fun getTvDetail(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<DetailTvResponse>

    @GET(ApiUrl.RECOMMENDATION)
    fun getRecomendedMovies(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<MoviesResponse>

    @GET(ApiUrl.RECOMMENDATION_TV)
    fun getRecomendedTv(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<TvResponse>

    @GET(ApiUrl.REVIEWS)
    fun getReviews(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<ReviewResponse>

    @GET(ApiUrl.REVIEWS_TV)
    fun getReviewsTV(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Observable<ReviewResponse>

    @GET(ApiUrl.CREDITS)
    fun getCredits(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<CastResponse>

    @GET("tv/{tv_id}/credits")
    fun getCreditsTv(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<CastResponse>

    @GET(ApiUrl.VIDEO_MOVIE)
    fun getVideosMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ) : Single<VideoResponse>

    @GET(ApiUrl.VIDEO_TV)
    fun getVideosTv(
    @Path("tv_id") tv_id: Int,
    @Query("api_key") api_key: String ,
    @Query("language") language: String
    ) : Single<VideoResponse>

    @GET(ApiUrl.PERSON_FILM)
    fun getFilmography(
        @Path("person_id") person_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<PersonFilmResponse>

    @GET(ApiUrl.BIOGRAPHY)
    fun getPerson(
        @Path("person_id") person_id: Int,
        @Query("api_key") api_key: String
    ) : Observable<PersonResponse>
}