package com.aldebaran.domain.entities.mapper

import com.aldebaran.domain.entities.remote.VideoResponse
import com.aldebaran.domain.entities.ui.Video

fun VideoResponse.toVideo() = Video(
    id = id.orEmpty(),
    key = key.orEmpty(),
    name = name.orEmpty(),
    type = type.orEmpty()
)

fun VideoResponse?.orEmpty() = this ?: Video("","","","")