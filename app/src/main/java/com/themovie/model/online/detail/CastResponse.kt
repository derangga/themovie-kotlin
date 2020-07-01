package com.themovie.model.online.detail

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("cast") val credits: List<Credits>
)

data class Credits(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("character") val character: String? = "",
    @SerializedName("profile_path") val profilePath: String? = ""
)