package com.themovie.model.online

import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.upcoming.UpcomingResponse

data class MainData(
    val trending: TvResponse,
    val upcomingResponse: UpcomingResponse,
    val tvResponse: TvResponse,
    val moviesResponse: MoviesResponse
)