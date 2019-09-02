package com.themovie.model.online.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results") val videos: List<Videos>
)

data class Videos(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String
)