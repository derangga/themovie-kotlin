package com.themovie.model.online.upcoming

import com.google.gson.annotations.SerializedName
import com.themovie.model.online.discovermv.Movies

data class UpcomingResponse (
    @SerializedName("results") val results: List<Movies>,
    @SerializedName("total_pages") val totalPages: Int
)