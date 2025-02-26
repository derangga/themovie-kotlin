package com.themovie.datasource.repository.remote.source

import com.themovie.datasource.BuildConfig
import com.themovie.datasource.repository.remote.service.ArtistServices
import com.themovie.datasource.entities.mapper.orEmpty
import com.themovie.datasource.entities.mapper.toArtist
import com.themovie.datasource.entities.mapper.toArtistFilm
import com.themovie.datasource.entities.mapper.toArtistPict
import com.themovie.datasource.entities.ui.Artist
import com.themovie.datasource.entities.ui.ArtistFilm
import com.themovie.datasource.entities.ui.ArtistPict
import com.themovie.datasource.repository.remote.ArtistRemoteSource
import com.themovie.core.network.Result
import com.themovie.core.network.safeCallApi
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