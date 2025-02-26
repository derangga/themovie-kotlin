package com.themovie.datasource.entities.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val type: String
) : Parcelable