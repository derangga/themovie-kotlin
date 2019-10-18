package com.themovie.model.online

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovertv.TvResponse

data class FetchDetailTvData(
    val detailTvResponse: DetailTvResponse?,
    val castResponse: CastResponse?,
    val tvResponse: TvResponse?,
    val reviews: ReviewResponse?
)