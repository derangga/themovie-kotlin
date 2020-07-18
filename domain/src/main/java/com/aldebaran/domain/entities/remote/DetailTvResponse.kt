package com.aldebaran.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("vote_average") val voteAverage: String? = "",
    @SerializedName("vote_count") val voteCount: Int? = 0,
    @SerializedName("status") val status: String? = "",
    @SerializedName("popularity") val popularity: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("first_air_date") val firstAirDate: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int? = 0,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int? = 0,
    @SerializedName("genres") val genreList: List<GenreEntity>,
    @SerializedName("seasons") val seasons: List<SeasonTv>
)

data class SeasonTv(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("episode_count") val totalEpisode: Int,
    @SerializedName("poster_path") val posterPath: String?
)