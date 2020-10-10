package com.themovie.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.person.PersonFilmResponse
import com.aldebaran.domain.entities.remote.person.PersonImageResponse
import com.aldebaran.domain.entities.remote.person.PersonResponse
import com.aldebaran.domain.repository.remote.ArtistRemoteSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PersonViewModel @ViewModelInject constructor(
    private val remote: ArtistRemoteSource
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