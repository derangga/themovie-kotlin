package com.themovie.model.online

import com.google.gson.annotations.SerializedName

data class UpcomingResponse (
    @SerializedName("results") val results: List<Movies>,
    @SerializedName("total_pages") val totalPages: Int
)