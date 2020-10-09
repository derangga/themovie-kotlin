package com.aldebaran.domain.entities.remote

import com.aldebaran.domain.entities.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("id") override val id: Int?,
    @SerializedName("title") override val title: String?,
    @SerializedName("poster_path") override val posterPath: String?,
    @SerializedName("backdrop_path") override val backdropPath: String?,
    @SerializedName("overview") override val overview: String?,
    @SerializedName("vote_average") override val voteAverage: String?,
    @SerializedName("release_date") override val releaseDate: String?
) : Movie