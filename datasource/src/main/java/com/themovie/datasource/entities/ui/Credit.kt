package com.themovie.datasource.entities.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Credit(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String
) : Parcelable