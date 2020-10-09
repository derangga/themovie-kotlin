package com.aldebaran.domain.entities.remote.person

import com.google.gson.annotations.SerializedName

data class PersonFilmResponse(
    @SerializedName("cast") val filmographies: List<Filmography>
)

data class Filmography(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("title") val title: String? = "",
    @SerializedName("character") val character: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdrop_path: String? = "",
    @SerializedName("vote_average") val rating: String? = ""
)