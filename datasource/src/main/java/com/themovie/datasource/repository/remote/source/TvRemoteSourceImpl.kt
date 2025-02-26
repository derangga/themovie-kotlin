package com.themovie.datasource.repository.remote.source

import com.themovie.datasource.BuildConfig
import com.themovie.datasource.repository.remote.service.TvServices
import com.themovie.datasource.Constant
import com.themovie.datasource.entities.mapper.toDetailTv
import com.themovie.datasource.entities.mapper.toCast
import com.themovie.datasource.entities.mapper.toReview
import com.themovie.datasource.entities.mapper.toTv
import com.themovie.datasource.entities.mapper.toVideo
import com.themovie.datasource.entities.mapper.orEmpty
import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.DetailTv
import com.themovie.datasource.entities.ui.Tv
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video
import com.themovie.datasource.repository.remote.TvRemoteSource
import com.themovie.core.network.Result
import com.themovie.core.network.safeCallApi
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