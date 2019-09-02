package com.themovie.repos.fromapi

import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class PersonRepos {

    fun getPersonData(token: String, personId: Int): Observable<FetchPersonData> {
        val personDetail = ApiClient.getApiBuilder().getPerson(personId, token).subscribeOn(Schedulers.io())
        val personFilm = ApiClient.getApiBuilder().getFilmography(personId, token).subscribeOn(Schedulers.io())
        val call: Observable<FetchPersonData> = Observable.zip(personDetail, personFilm,
            object: BiFunction<PersonResponse, PersonFilmResponse, FetchPersonData>{
                override fun apply(t1: PersonResponse, t2: PersonFilmResponse): FetchPersonData {
                    return FetchPersonData(t1, t2)
                }
            })
        return call
    }
}