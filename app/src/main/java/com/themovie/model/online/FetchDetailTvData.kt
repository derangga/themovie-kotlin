package com.themovie.model.online

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.video.VideoResponse

data class FetchDetailTvData(
    val detailTvResponse: DetailTvResponse?,
    val castResponse: CastResponse?,
    val videoResponse: VideoResponse?,
    val tvResponse: TvResponse?,
    val reviews: ReviewResponse?
)