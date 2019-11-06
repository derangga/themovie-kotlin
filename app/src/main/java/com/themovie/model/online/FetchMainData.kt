package com.themovie.model.online

import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.upcoming.UpcomingResponse

data class FetchMainData(
    val popular: PopularResponse?,
    val genre: GenreResponse?,
    val upcomingResponse: UpcomingResponse?,
    val tvResponse: TvResponse?,
    val moviesResponse: MoviesResponse?
)