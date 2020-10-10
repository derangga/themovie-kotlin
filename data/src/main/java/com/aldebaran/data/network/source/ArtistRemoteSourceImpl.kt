package com.aldebaran.data.network.source

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.safeCallApi
import com.aldebaran.data.network.service.ArtistServices
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.person.PersonFilmResponse
import com.aldebaran.domain.entities.remote.person.PersonImageResponse
import com.aldebaran.domain.entities.remote.person.PersonResponse
import com.aldebaran.domain.repository.remote.ArtistRemoteSource

class ArtistRemoteSourceImpl(
    private val artistServices: ArtistServices
) : ArtistRemoteSource {
    override suspend fun getDetailPerson(personId: Int): Result<PersonResponse> {
        return safeCallApi { artistServices.getPerson(personId, BuildConfig.TOKEN) }
    }

    override suspend fun getPersonFilm(personId: Int): Result<PersonFilmResponse> {
        return safeCallApi { artistServices.getPersonFilm(personId, BuildConfig.TOKEN) }
    }

    override suspend fun getPersonPict(personId: Int): Result<PersonImageResponse> {
        return safeCallApi { artistServices.getPersonImages(personId, BuildConfig.TOKEN) }
    }
}