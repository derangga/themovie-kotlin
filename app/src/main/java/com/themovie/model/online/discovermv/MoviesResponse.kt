package com.themovie.model.online.discovermv

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val movies: List<Movies>,
    @SerializedName("total_pages") val totalPages: String,
    @SerializedName("page") val page: String
)