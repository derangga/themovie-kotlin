package com.themovie.datasource.entities.mapper

import com.themovie.datasource.entities.remote.VideoResponse
import com.themovie.datasource.entities.ui.Video

fun VideoResponse.toVideo() = Video(
    id = id.orEmpty(),
    key = key.orEmpty(),
    name = name.orEmpty(),
    type = type.orEmpty()
)

fun VideoResponse?.orEmpty() = this ?: Video("","","","")