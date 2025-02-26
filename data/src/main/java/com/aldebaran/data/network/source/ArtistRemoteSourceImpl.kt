package com.aldebaran.data.network.source

import com.aldebaran.data.BuildConfig
import com.aldebaran.data.network.service.ArtistServices
import com.aldebaran.domain.entities.mapper.orEmpty
import com.aldebaran.domain.entities.mapper.toArtist
import com.aldebaran.domain.entities.mapper.toArtistFilm
import com.aldebaran.domain.entities.mapper.toArtistPict
import com.aldebaran.domain.entities.ui.Artist
import com.aldebaran.domain.entities.ui.ArtistFilm
import com.aldebaran.domain.entities.ui.ArtistPict
import com.aldebaran.domain.repository.remote.ArtistRemoteSource
import com.aldebaran.network.Result
import com.aldebaran.network.safeCallApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ArtistRemoteSourceImpl(
    private val artistServices: ArtistServices
) : ArtistRemoteSource {
    override suspend fun getDetailPerson(personId: Int): Result<Artist> {
        return withContext(IO) {
            safeCallApi(
                call = { artistServices.getPerson(personId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.toArtist().orEmpty() }
            )
        }
    }

    override suspend fun getPersonFilm(personId: Int): Result<List<ArtistFilm>> {
        return withContext(IO) {
            safeCallApi(
                call = { artistServices.getPersonFilm(personId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.filmographyResponses?.map { it.toArtistFilm() }.orEmpty() }
            )
        }
    }

    override suspend fun getPersonPict(personId: Int): Result<List<ArtistPict>> {
        return withContext(IO) {
            safeCallApi(
                call = { artistServices.getPersonImages(personId, BuildConfig.TOKEN) },
                onSuccess = { body -> body?.imageResponseList?.map { it.toArtistPict() }.orEmpty() }
            )
        }
    }
}