package com.aldebaran.domain.entities.mapper

import com.aldebaran.domain.entities.remote.ReviewsResponse
import com.aldebaran.domain.entities.ui.Review

fun ReviewsResponse.toReview() = Review(
    id = id.orEmpty(),
    author = author.orEmpty(),
    content = content.orEmpty(),
    url = url.orEmpty()
)

fun ReviewsResponse?.orEmpty() = this ?: Review("", "", "", "")