package com.themovie.model.online.person

import com.google.gson.annotations.SerializedName

data class PersonFilmResponse(
    @SerializedName("cast") val filmographies: List<Filmography>
)

data class Filmography(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("character") val character: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdrop_path: String?
)