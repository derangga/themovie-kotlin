package com.themovie.datasource.repository.remote.service

import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.ResponseParser
import com.themovie.datasource.entities.remote.CastResponse
import com.themovie.datasource.entities.remote.DetailMovieResponse
import com.themovie.datasource.entities.remote.GenresResponse
import com.themovie.datasource.entities.remote.MovieResponse
import com.themovie.datasource.entities.remote.ReviewsResponse
import com.themovie.datasource.entities.remote.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET(ApiUrl.UPCOMING)
    suspend fun getUpcomingMovies (
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String = "US"
    ) : Response<ResponseParser<MovieResponse>>

    @GET(ApiUrl.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<ResponseParser<MovieResponse>>

    @GET(ApiUrl.GENRES)
    suspend fun getGenres(@Query("api_key") api_key: String): Response<GenresResponse>

    @GET(ApiUrl.DISCOVER_MOVIES)
    suspend fun getDiscoverMovies (
        @Query("api_key") apiKey: String ,
        @Query("sort_by") sortBy: String ,
        @Query("page") page: Int ,
        @Query("primary_release_year") primaryReleaseYear: Int,
        @Query("with_genres") withGenres: String
    ) : Response<ResponseParser<MovieResponse>>

    @GET(ApiUrl.DETAIL_MOVIE)
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Response<DetailMovieResponse>

    @GET(ApiUrl.RECOMMENDATION_MOVIE)
    suspend fun getRecommendationMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<MovieResponse>>

    @GET(ApiUrl.REVIEWS)
    suspend fun getReviewsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<ReviewsResponse>>

    @GET(ApiUrl.CREDITS)
    suspend fun getCreditsMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Response<CastResponse>

    @GET(ApiUrl.VIDEO_MOVIE)
    suspend fun getTrailerMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Response<VideosResponse>

    @GET(ApiUrl.SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<MovieResponse>>
}