package com.aldebaran.domain.entities.mapper

import com.aldebaran.domain.entities.remote.CreditsResponse
import com.aldebaran.domain.entities.remote.person.FilmographyResponse
import com.aldebaran.domain.entities.remote.person.PersonImageResponse
import com.aldebaran.domain.entities.remote.person.PersonResponse
import com.aldebaran.domain.entities.ui.Artist
import com.aldebaran.domain.entities.ui.ArtistFilm
import com.aldebaran.domain.entities.ui.ArtistPict
import com.aldebaran.domain.entities.ui.Credit

fun CreditsResponse.toCast() = Credit(
    id = id ?: 0,
    name = name.orEmpty(),
    character = character.orEmpty(),
    profilePath = profilePath.orEmpty()
)

fun PersonResponse.toArtist() = Artist(
    id = id ?: 0,
    name = name.orEmpty(),
    birthday = birthday.orEmpty(),
    placeOfBirth = placeOfBirth.orEmpty(),
    profilePath = profilePath.orEmpty(),
    biography = biography.orEmpty(),
    gender = gender.orEmpty()
)

fun Artist?.orEmpty() = this ?: Artist(
    0,"","","",
    "","",""
)

fun FilmographyResponse.toArtistFilm() = ArtistFilm(
    id = id ?: 0,
    title = title.orEmpty(),
    character = character.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    rating = rating.orEmpty()
)

fun PersonImageResponse.toArtistPict() = ArtistPict(
    width = width ?: 0,
    height = height ?: 0,
    filePath = filePath.orEmpty()
)