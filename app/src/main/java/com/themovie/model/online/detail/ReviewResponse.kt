package com.themovie.model.online.detail

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val totalPages: Int,
    @SerializedName("results") val reviews: List<Reviews>
)

data class Reviews(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("url") val url: String
)