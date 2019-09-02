package com.themovie.model.online.detail

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("vote_average") val voteAverage: String,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("status") val status: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("genres") val genreList: List<Genre>,
    @SerializedName("seasons") val seasons: List<SeasonTv>
)

data class SeasonTv(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("episode_count") val totalEpisode: Int,
    @SerializedName("poster_path") val posterPath: String?
)