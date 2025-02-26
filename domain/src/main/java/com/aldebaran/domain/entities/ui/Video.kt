package com.aldebaran.domain.entities.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val type: String
) : Parcelable