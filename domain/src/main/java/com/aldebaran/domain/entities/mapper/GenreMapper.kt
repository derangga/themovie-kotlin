package com.aldebaran.domain.entities.mapper

import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.entities.remote.GenreResponse
import com.aldebaran.domain.entities.ui.Genre

fun GenreResponse.toGenre() = Genre(
    id = id ?: 0,
    name = name.orEmpty()
)

fun GenreEntity.toGenre() = Genre(
    id = id ?: 0,
    name = name.orEmpty()
)

fun Genre.toGenreEntity(pkId: Int? = null): GenreEntity {
    return GenreEntity(
        pkId = pkId,
        id = id,
        name = name
    )
}

fun GenreResponse?.orEmpty() = this ?: Genre(0, "")
fun GenreEntity?.orEmpty() = this ?: Genre(0, "")