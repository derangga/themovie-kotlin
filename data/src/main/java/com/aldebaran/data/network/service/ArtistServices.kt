package com.aldebaran.data.network.service

import com.aldebaran.data.network.ApiUrl
import com.aldebaran.domain.entities.remote.person.PersonFilmResponse
import com.aldebaran.domain.entities.remote.person.PersonImageResponse
import com.aldebaran.domain.entities.remote.person.PersonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistServices {
    @GET(ApiUrl.PERSON_FILM)
    suspend fun getPersonFilm(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonFilmResponse>

    @GET(ApiUrl.BIOGRAPHY)
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonResponse>

    @GET(ApiUrl.PERSON_IMG)
    suspend fun getPersonImages(
        @Path("person_id") personId: Int,
        @Query("api_key") api_key: String
    ) : Response<PersonImageResponse>
}