package com.themovie.model.online

import com.google.gson.annotations.SerializedName
import com.themovie.model.db.Trending

data class PopularResponse (
    @SerializedName("results") val results: List<Trending>,
    @SerializedName("total_pages") val totalPages: Int
)