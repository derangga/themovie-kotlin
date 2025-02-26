package com.themovie.datasource.entities.remote

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("original_title") val title: String? = "",
    @SerializedName("vote_average") val rate: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("status") val status: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("popularity") val popularity: String? = "",
    @SerializedName("vote_count") val voteCount: String? = "",
    @SerializedName("genres") val genreList: List<GenreResponse>?
)