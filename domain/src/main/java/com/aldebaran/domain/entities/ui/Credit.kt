package com.aldebaran.domain.entities.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credit(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String
) : Parcelable