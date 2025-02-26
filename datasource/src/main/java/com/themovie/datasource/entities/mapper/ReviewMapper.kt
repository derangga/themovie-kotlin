package com.themovie.datasource.entities.mapper

import com.themovie.datasource.entities.remote.ReviewsResponse
import com.themovie.datasource.entities.ui.Review

fun ReviewsResponse.toReview() = Review(
    id = id.orEmpty(),
    author = author.orEmpty(),
    content = content.orEmpty(),
    url = url.orEmpty()
)

fun ReviewsResponse?.orEmpty() = this ?: Review("", "", "", "")