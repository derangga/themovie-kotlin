package com.aldebaran.domain.repository.remote

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.person.PersonFilmResponse
import com.aldebaran.domain.entities.remote.person.PersonImageResponse
import com.aldebaran.domain.entities.remote.person.PersonResponse

interface ArtistRemoteSource {

    suspend fun getDetailPerson(personId: Int): Result<PersonResponse>

    suspend fun getPersonFilm(personId: Int): Result<PersonFilmResponse>

    suspend fun getPersonPict(personId: Int): Result<PersonImageResponse>
}