package com.aldebaran.domain.repository.remote

import com.aldebaran.network.Result
import com.aldebaran.domain.entities.ui.*

interface TvRemoteSource {

    suspend fun getDiscoverTv(page: Int): Result<List<Tv>>

    suspend fun getDetailTv(tvId: Int): Result<DetailTv>

    suspend fun getRecommendationTv(tvId: Int): Result<List<Tv>>

    suspend fun getReviewTv(tvId: Int, page: Int): Result<List<Review>>

    suspend fun getCreditsTv(tvId: Int): Result<List<Credit>>

    suspend fun getTrailerTv(tvId: Int): Result<List<Video>>

    suspend fun searchTv(query: String, page: Int): Result<List<Tv>>
}