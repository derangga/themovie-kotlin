package com.aldebaran.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("id") val id: String? = "",
    @SerializedName("author") val author: String? = "",
    @SerializedName("content") val content: String? = "",
    @SerializedName("url") val url: String? = ""
)