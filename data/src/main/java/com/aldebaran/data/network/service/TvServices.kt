package com.aldebaran.data.network.service

import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.ResponseParser
import com.aldebaran.domain.entities.remote.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvServices {
    @GET(ApiUrl.DISCOVER_TV)
    suspend fun getDiscoverTv(
        @Query("api_key") api_key: String,
        @Query("sort_by") sort_by: String,
        @Query("timezone") timezone: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>

    @GET(ApiUrl.DETAIL_TV)
    suspend fun getTvDetail(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String
    ) : Response<DetailTvResponse>

    @GET(ApiUrl.RECOMMENDATION_TV)
    suspend fun getRecommendationTv(
        @Path("tv_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>

    @GET(ApiUrl.REVIEWS_TV)
    suspend fun getReviewsTV(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<ReviewsResponse>>

    @GET(ApiUrl.CREDITS_TV)
    suspend fun getCreditsTv(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ) : Response<CastResponse>

    @GET(ApiUrl.VIDEO_TV)
    suspend fun getTrailerTv(
        @Path("tv_id") tv_id: Int,
        @Query("api_key") api_key: String
    ) : Response<VideosResponse>

    @GET(ApiUrl.SEARCH_TV)
    suspend fun getSearchTv(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Response<ResponseParser<TvResponse>>
}