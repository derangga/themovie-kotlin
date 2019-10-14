package com.themovie.repos.fromapi

import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.restapi.ApiInterface
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PersonRepos
@Inject constructor(private val apiInterface: ApiInterface){

    fun getPersonData(token: String, personId: Int): Observable<FetchPersonData> {
        val personDetail = apiInterface.getPerson(personId, token).subscribeOn(Schedulers.io())
        val personFilm = apiInterface.getFilmography(personId, token).subscribeOn(Schedulers.io())
        return Observable.zip(personDetail, personFilm,
            object: BiFunction<PersonResponse, PersonFilmResponse, FetchPersonData>{
                override fun apply(t1: PersonResponse, t2: PersonFilmResponse): FetchPersonData {
                    return FetchPersonData(t1, t2)
                }
            })
    }
}