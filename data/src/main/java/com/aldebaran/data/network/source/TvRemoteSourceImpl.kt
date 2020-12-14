package com.aldebaran.data.network.source

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.service.TvServices
import com.aldebaran.domain.Constant
import com.aldebaran.domain.entities.mapper.*
import com.aldebaran.domain.entities.ui.*
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.network.Result
import com.aldebaran.network.safeCallApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TvRemoteSourceImpl(
    private val services: TvServices
): TvRemoteSource {
    override suspend fun getDiscoverTv(page: Int): Result<List<Tv>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getDiscoverTv(BuildConfig.TOKEN, Constant.SORTING, Constant.TIMEZONE, page) },
                onSuccess = { body -> body?.results?.map { it.toTv() }.orEmpty() }
            )
        }
    }

    override suspend fun getDetailTv(tvId: Int): Result<DetailTv> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getTvDetail(tvId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.toDetailTv().orEmpty() }
            )
        }
    }

    override suspend fun getRecommendationTv(tvId: Int): Result<List<Tv>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getRecommendationTv(tvId, BuildConfig.TOKEN, 1) },
                onSuccess = { body -> body?.results?.map { it.toTv() }.orEmpty() }
            )
        }
    }

    override suspend fun getReviewTv(tvId: Int, page: Int): Result<List<Review>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getReviewsTV(tvId, BuildConfig.TOKEN, 1) },
                onSuccess = { body -> body?.results?.map { it.toReview() }.orEmpty() }
            )
        }
    }

    override suspend fun getCreditsTv(tvId: Int): Result<List<Credit>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getCreditsTv(tvId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.credits?.map { it.toCast() }.orEmpty() }
            )
        }
    }

    override suspend fun getTrailerTv(tvId: Int): Result<List<Video>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getTrailerTv(tvId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.videos?.map { it.toVideo() }.orEmpty() }
            )
        }
    }

    override suspend fun searchTv(query: String, page: Int): Result<List<Tv>> {
        return withContext(IO) {
            safeCallApi(
                call = { services.getSearchTv(BuildConfig.TOKEN, query, page) },
                onSuccess = { body -> body?.results?.map { it.toTv() }.orEmpty() }
            )
        }
    }
}