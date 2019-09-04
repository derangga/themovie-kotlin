package com.themovie.model.online.person

import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("biography") val biography: String?,
    @SerializedName("gender") val gender: String
)