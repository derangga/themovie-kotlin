package com.themovie.ui.person

import androidx.lifecycle.*
import com.themovie.model.online.person.PersonFilmResponse
import com.themovie.model.online.person.PersonImageResponse
import com.themovie.model.online.person.PersonResponse
import com.themovie.repos.RemoteSource
import com.themovie.restapi.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val remote: RemoteSource
) : ViewModel() {

    private val _detailPerson by lazy { MutableLiveData<Result<PersonResponse>>() }
    private val _personFilm by lazy { MutableLiveData<Result<PersonFilmResponse>>() }
    private val _personPict by lazy { MutableLiveData<Result<PersonImageResponse>>() }

    val detailPerson: LiveData<Result<PersonResponse>> get() = _detailPerson
    val personFilm: LiveData<Result<PersonFilmResponse>> get() = _personFilm
    val personPict: LiveData<Result<PersonImageResponse>> get() = _personPict

    fun getDetailPersonRequest(personId: Int){
        viewModelScope.launch {
            val person = async(IO) { remote.getDetailPerson(personId) }
            val pict = async(IO) { remote.getPersonPict(personId) }
            val film = async(IO) { remote.getPersonFilm(personId) }

            _detailPerson.value = person.await()
            _personPict.value = pict.await()
            _personFilm.value = film.await()
        }
    }
}