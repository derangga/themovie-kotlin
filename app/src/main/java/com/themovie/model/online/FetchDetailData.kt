package com.themovie.model.online

import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse

data class FetchDetailData(
    val detailMovieResponse: DetailMovieResponse,
    val castResponse: CastResponse,
    val moviesResponse: MoviesResponse,
    val reviewResponse: ReviewResponse
)