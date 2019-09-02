package com.themovie.model.online

import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonResponse

data class FetchPersonData (
    val personResponse: PersonResponse,
    val personFilmResponse: PersonFilmResponse
)