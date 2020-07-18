package com.aldebaran.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("page") val totalPages: Int? = 0,
    @SerializedName("results") val reviewList: List<Reviews>
)

data class Reviews(
    @SerializedName("id") val id: String? = "",
    @SerializedName("author") val author: String? = "",
    @SerializedName("content") val content: String? = "",
    @SerializedName("url") val url: String? = ""
)