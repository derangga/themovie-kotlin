package com.aldebaran.domain.entities.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val id: Int,
    val name: String,
    val birthday: String,
    val placeOfBirth: String,
    val profilePath: String,
    val biography: String,
    val gender: String
) : Parcelable

@Parcelize
data class ArtistFilm(
    val id: Int,
    val title: String,
    val character: String,
    val posterPath: String,
    val backdropPath: String,
    val rating: String
) : Parcelable

@Parcelize
data class ArtistPict(
    val width: Int,
    val height: Int,
    val filePath: String
) : Parcelable