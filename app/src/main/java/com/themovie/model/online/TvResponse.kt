package com.themovie.model.online

import com.google.gson.annotations.SerializedName

data class TvResponse (
    @SerializedName("results") val results: List<Tv>,
    @SerializedName("total_pages") val totalPages: Int
)