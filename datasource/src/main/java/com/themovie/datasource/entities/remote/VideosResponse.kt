package com.themovie.datasource.entities.remote

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("results") val videos: List<VideoResponse>
)

data class VideoResponse(
    @SerializedName("id") val id: String? = "",
    @SerializedName("key") val key: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("type") val type: String? = ""
)