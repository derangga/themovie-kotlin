package com.aldebaran.data.network.service

import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServices {

    @GET(ApiUrl.UPCOMING)
    suspend fun getUpcomingMovies (
        @Query("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("region") region: String = "US"
    ) : Response<DataList<MovieResponse>>

    @GET(ApiUrl.POPULAR_MOVIE)
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<DataList<MovieResponse>>

    @GET(ApiUrl.GENRES)
    suspend fun getGenres(@Query("api_key") api_key: String): Response<GenreResponse>

    @GET(ApiUrl.DISCOVER_MOVIES)
    suspend fun getDiscoverMovies (
        @Query("api_key") api_key: String ,
        @Query("sort_by") sort_by: String ,
        @Query("page") page: Int ,
        @Query("primary_release_year") primaryReleaseYear: Int,
        @Query("with_genres") withGenres: String
    ) : Response<DataList<MovieResponse>>

    @GET(ApiUrl.DETAIL_MOVIE)
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<DetailMovieResponse>

    @GET(ApiUrl.RECOMMENDATION_MOVIE)
    suspend fun getRecommendationMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Response<DataList<MovieResponse>>

    @GET(ApiUrl.REVIEWS)
    suspend fun getReviewsMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Response<DataList<ReviewsResponse>>

    @GET(ApiUrl.CREDITS)
    suspend fun getCreditsMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<CastResponse>

    @GET(ApiUrl.VIDEO_MOVIE)
    suspend fun getTrailerMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<VideoResponse>

    @GET(ApiUrl.SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<DataList<MovieResponse>>
}