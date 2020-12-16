package com.aldebaran.domain.entities.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val overview: String,
    val voteAverage: String,
    val releaseDate: String
) : Parcelable

@Parcelize
data class DetailMovie(
    val id: Int,
    val title: String,
    val rate: String,
    val overview: String,
    val status: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: String,
    val voteCount: String,
    val genreList: List<Genre>
) : Parcelable