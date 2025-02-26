package com.themovie.datasource.entities.mapper

import com.themovie.datasource.entities.local.GenreEntity
import com.themovie.datasource.entities.remote.GenreResponse
import com.themovie.datasource.entities.ui.Genre

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