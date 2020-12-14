package com.aldebaran.domain.repository.remote

import com.aldebaran.network.Result
import com.aldebaran.domain.entities.ui.Artist
import com.aldebaran.domain.entities.ui.ArtistFilm
import com.aldebaran.domain.entities.ui.ArtistPict

interface ArtistRemoteSource {

    suspend fun getDetailPerson(personId: Int): Result<Artist>

    suspend fun getPersonFilm(personId: Int): Result<List<ArtistFilm>>

    suspend fun getPersonPict(personId: Int): Result<List<ArtistPict>>
}