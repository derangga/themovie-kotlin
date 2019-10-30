package com.themovie.model.online

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.video.VideoResponse

data class FetchDetailMovieData(
    val detailMovieResponse: DetailMovieResponse?,
    val castResponse: CastResponse?,
    val videoResponse: VideoResponse?,
    val moviesResponse: MoviesResponse?,
    val reviewResponse: ReviewResponse?
)