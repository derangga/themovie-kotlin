package com.themovie.model.online.discovertv

import com.google.gson.annotations.SerializedName

data class Tv (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("vote_average") val voteAverage: String,
    @SerializedName("vote_count") val voteCount: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val overview: String
)