package com.aldebaran.domain.repository.remote

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*

interface TvRemoteSource {

    suspend fun getDiscoverTv(page: Int): Result<DataList<TvResponse>>

    suspend fun getDetailTv(tvId: Int): Result<DetailTvResponse>

    suspend fun getRecommendationTv(tvId: Int): Result<DataList<TvResponse>>

    suspend fun getReviewTv(tvId: Int, page: Int): Result<DataList<ReviewsResponse>>

    suspend fun getCreditsTv(tvId: Int): Result<CastResponse>

    suspend fun getTrailerTv(tvId: Int): Result<VideoResponse>

    suspend fun searchTv(query: String, page: Int): Result<DataList<TvResponse>>
}