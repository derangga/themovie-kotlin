package com.themovie.model.online.genre

import com.themovie.model.db.Genre

data class GenreResponse(
    val genres: List<Genre>
)
