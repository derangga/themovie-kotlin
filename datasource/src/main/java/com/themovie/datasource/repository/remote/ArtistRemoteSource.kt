package com.themovie.datasource.repository.remote

import com.themovie.core.network.Result
import com.themovie.datasource.entities.ui.Artist
import com.themovie.datasource.entities.ui.ArtistFilm
import com.themovie.datasource.entities.ui.ArtistPict

interface ArtistRemoteSource {

    suspend fun getDetailPerson(personId: Int): Result<Artist>

    suspend fun getPersonFilm(personId: Int): Result<List<ArtistFilm>>

    suspend fun getPersonPict(personId: Int): Result<List<ArtistPict>>
}