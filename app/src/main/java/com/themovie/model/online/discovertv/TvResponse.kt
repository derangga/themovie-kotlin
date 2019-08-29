package com.themovie.model.online.discovertv

import com.google.gson.annotations.SerializedName
import com.themovie.model.online.discovertv.Tv

data class TvResponse (
    @SerializedName("results") val results: List<Tv>,
    @SerializedName("total_pages") val totalPages: Int
)