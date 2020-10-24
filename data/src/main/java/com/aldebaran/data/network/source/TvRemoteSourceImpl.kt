package com.aldebaran.data.network.source

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.ApiUrl
import com.aldebaran.data.network.safeCallApi
import com.aldebaran.data.network.service.TvServices
import com.aldebaran.domain.Constant
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*
import com.aldebaran.domain.repository.remote.TvRemoteSource

class TvRemoteSourceImpl(
    private val tvServices: TvServices
): TvRemoteSource {
    override suspend fun getDiscoverTv(
        page: Int
    ): Result<DataList<TvResponse>> {
        return safeCallApi {
            tvServices.getDiscoverTv(BuildConfig.TOKEN, Constant.SORTING, Constant.TIMEZONE, page)
        }
    }

    override suspend fun getDetailTv(tvId: Int): Result<DetailTvResponse> {
        return safeCallApi {
            tvServices.getTvDetail(tvId, BuildConfig.TOKEN)
        }
    }

    override suspend fun getRecommendationTv(tvId: Int): Result<DataList<TvResponse>> {
        return safeCallApi {
            tvServices.getRecommendationTv(tvId, BuildConfig.TOKEN, 1)
        }
    }

    override suspend fun getReviewTv(tvId: Int, page: Int): Result<DataList<ReviewsResponse>> {
        return safeCallApi {
            tvServices.getReviewsTV(tvId, BuildConfig.TOKEN, 1)
        }
    }

    override suspend fun getCreditsTv(tvId: Int): Result<CastResponse> {
        return safeCallApi {
            tvServices.getCreditsTv(tvId, BuildConfig.TOKEN)
        }
    }

    override suspend fun getTrailerTv(tvId: Int): Result<VideoResponse> {
        return safeCallApi {
            tvServices.getTrailerTv(tvId, BuildConfig.TOKEN)
        }
    }

    override suspend fun searchTv(query: String, page: Int): Result<DataList<TvResponse>> {
        return safeCallApi {
            tvServices.getSearchTv(BuildConfig.TOKEN, query, page)
        }
    }
}