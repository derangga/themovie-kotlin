package com.themovie.restapi

import com.themovie.model.online.PopularResponse
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonImageResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.model.online.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiUrl.UPCOMING)
    suspend fun getUpcomingMovies (
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("region") region: String = "US"
    ) : Response<UpcomingResponse>

    @GET(ApiUrl.TRENDING)
    suspend fun getTrendingTv(@Query("api_key") api_key: String): Response<TvResponse>

    @GET(ApiUrl.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<PopularResponse>

    @GET(ApiUrl.GENRES)
    suspend fun getGenres(@Query("api_key") api_key: String): Response<GenreResponse>

    @GET(ApiUrl.DISCOVER_MOVIES)
    suspend fun getDiscoverMovies (
        @Query("api_key") api_key: String ,
        @Query("language") language: String ,
        @Query("sort_by") sort_by: String ,
        @Query("page") page: Int ,
        @Query("primary_release_year") primary_release_year: String ,
        @Query("with_genres") withGenres: String
    ) : Response<MoviesResponse>

    @GET(ApiUrl.DISCOVER_TV)
    suspend fun getDiscoverTvs(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("page") page: Int,
        @Query("timezone") timezone: String
    ) : Response<TvResponse>

    @GET(ApiUrl.DETAIL_MOVIE)
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<DetailMovieResponse>

    @GET(ApiUrl.DETAIL_TV)
    suspend fun getTvDetail(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<DetailTvResponse>

    @GET(ApiUrl.RECOMMENDATION)
    suspend fun getRecommendationMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<MoviesResponse>

    @GET(ApiUrl.RECOMMENDATION_TV)
    suspend fun getRecommendationTv(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<TvResponse>

    @GET(ApiUrl.REVIEWS)
    suspend fun getReviewsMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<ReviewResponse>

    @GET(ApiUrl.REVIEWS_TV)
    suspend fun getReviewsTV(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Response<ReviewResponse>

    @GET(ApiUrl.CREDITS)
    suspend fun getCreditsMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<CastResponse>

    @GET(ApiUrl.CREDITS_TV)
    suspend fun getCreditsTv(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ) : Response<CastResponse>

    @GET(ApiUrl.VIDEO_MOVIE)
    suspend fun getTrailerMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ) : Response<VideoResponse>

    @GET(ApiUrl.VIDEO_TV)
    suspend fun getTrailerTv(
    @Path("tv_id") tv_id: Int,
    @Query("api_key") api_key: String ,
    @Query("language") language: String
    ) : Response<VideoResponse>

    @GET(ApiUrl.PERSON_FILM)
    suspend fun getPersonFilm(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonFilmResponse>

    @GET(ApiUrl.BIOGRAPHY)
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonResponse>

    @GET(ApiUrl.PERSON_IMG)
    suspend fun getPersonImages(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonImageResponse>

    @GET(ApiUrl.SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<MoviesResponse>

    @GET(ApiUrl.SEARCH_TV)
    suspend fun getSearchTv(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<TvResponse>
}