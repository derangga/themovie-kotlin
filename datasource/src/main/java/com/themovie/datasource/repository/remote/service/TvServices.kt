package com.themovie.datasource.repository.remote.service

import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.ResponseParser
import com.themovie.datasource.entities.remote.CastResponse
import com.themovie.datasource.entities.remote.DetailTvResponse
import com.themovie.datasource.entities.remote.TvResponse
import com.themovie.datasource.entities.remote.ReviewsResponse
import com.themovie.datasource.entities.remote.VideosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvServices {
    @GET(ApiUrl.DISCOVER_TV)
    suspend fun getDiscoverTv(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("timezone") timezone: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>

    @GET(ApiUrl.DETAIL_TV)
    suspend fun getTvDetail(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String
    ) : Response<DetailTvResponse>

    @GET(ApiUrl.RECOMMENDATION_TV)
    suspend fun getRecommendationTv(
        @Path("tv_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>

    @GET(ApiUrl.REVIEWS_TV)
    suspend fun getReviewsTV(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<ReviewsResponse>>

    @GET(ApiUrl.CREDITS_TV)
    suspend fun getCreditsTv(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ) : Response<CastResponse>

    @GET(ApiUrl.VIDEO_TV)
    suspend fun getTrailerTv(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ) : Response<VideosResponse>

    @GET(ApiUrl.SEARCH_TV)
    suspend fun getSearchTv(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>
}