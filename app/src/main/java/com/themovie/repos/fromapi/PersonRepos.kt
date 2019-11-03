package com.themovie.repos.fromapi

import com.themovie.model.online.FetchPersonData
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class PersonRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getPersonData(token: String, personId: Int): FetchPersonData? {
        var data: FetchPersonData? = null
        try {
            coroutineScope {
                val detail = async(IO) { return@async apiInterface.getPerson(personId, token) }
                val person = async(IO) { return@async apiInterface.getFilmography(personId, token) }
                if(detail.await().isSuccessful && person.await().isSuccessful){
                    data = FetchPersonData(detail.await().body(), person.await().body())
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }
}