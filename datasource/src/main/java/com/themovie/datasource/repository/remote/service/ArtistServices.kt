package com.themovie.datasource.repository.remote.service

import com.themovie.datasource.repository.remote.ApiUrl
import com.themovie.datasource.entities.remote.person.PersonFilmResponse
import com.themovie.datasource.entities.remote.person.ProfilesResponse
import com.themovie.datasource.entities.remote.person.PersonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistServices {
    @GET(ApiUrl.PERSON_FILM)
    suspend fun getPersonFilm(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ) : Response<PersonFilmResponse>

    @GET(ApiUrl.BIOGRAPHY)
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ) : Response<PersonResponse>

    @GET(ApiUrl.PERSON_IMG)
    suspend fun getPersonImages(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ) : Response<ProfilesResponse>
}