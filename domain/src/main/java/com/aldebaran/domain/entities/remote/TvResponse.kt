package com.aldebaran.domain.entities.remote

import com.aldebaran.domain.entities.Tv
import com.google.gson.annotations.SerializedName

data class TvResponse(
    @SerializedName("id") override val id: Int?,
    @SerializedName("name") override val name: String?,
    @SerializedName("vote_average") override val voteAverage: String?,
    @SerializedName("vote_count") override val voteCount: String?,
    @SerializedName("poster_path") override val posterPath: String?,
    @SerializedName("backdrop_path") override val backdropPath: String?,
    @SerializedName("overview") override val overview: String?
): Tv