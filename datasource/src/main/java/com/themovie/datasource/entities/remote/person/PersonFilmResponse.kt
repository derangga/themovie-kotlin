package com.themovie.datasource.entities.remote.person

import com.google.gson.annotations.SerializedName

data class PersonFilmResponse(
    @SerializedName("cast") val filmographyResponses: List<FilmographyResponse>
)

data class FilmographyResponse(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("title") val title: String? = "",
    @SerializedName("character") val character: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("vote_average") val rating: String? = ""
)