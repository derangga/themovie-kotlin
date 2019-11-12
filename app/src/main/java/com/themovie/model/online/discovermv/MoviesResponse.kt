package com.themovie.model.online.discovermv

import com.google.gson.annotations.SerializedName
import com.themovie.model.db.Movies

data class MoviesResponse(
    @SerializedName("results") val movies: List<Movies>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("page") val page: Int
)