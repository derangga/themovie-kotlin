package com.themovie.datasource.repository.remote

import com.themovie.core.network.Result
import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.DetailTv
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video

interface TvRemoteSource {

    suspend fun getDiscoverTv(page: Int): Result<List<Tv>>

    suspend fun getDetailTv(tvId: Int): Result<DetailTv>

    suspend fun getRecommendationTv(tvId: Int): Result<List<Tv>>

    suspend fun getReviewTv(tvId: Int, page: Int): Result<List<Review>>

    suspend fun getCreditsTv(tvId: Int): Result<List<Credit>>

    suspend fun getTrailerTv(tvId: Int): Result<List<Video>>

    suspend fun searchTv(query: String, page: Int): Result<List<Tv>>
}