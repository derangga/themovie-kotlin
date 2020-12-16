package com.aldebaran.domain.entities.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tv(
    val id: Int,
    val name: String,
    val voteAverage: String,
    val voteCount: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String
) : Parcelable

@Parcelize
data class DetailTv(
    val id: Int,
    val name: String,
    val voteAverage: String,
    val voteCount: Int,
    val status: String,
    val popularity: String,
    val posterPath: String,
    val backdropPath: String,
    val firstAirDate: String,
    val overview: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val genreList: List<Genre>,
    val seasonResponses: List<SeasonTv>
) : Parcelable

@Parcelize
data class SeasonTv(
    val id: Int,
    val name: String,
    val totalEpisode: Int,
    val posterPath: String
) : Parcelable