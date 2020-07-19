package com.themovie.model.online.upcoming

import com.google.gson.annotations.SerializedName
import com.themovie.model.db.Upcoming

data class UpcomingResponse (
    @SerializedName("results") val results: List<Upcoming>,
    @SerializedName("total_pages") val totalPages: Int? = 0
)