package com.themovie.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.core.BaseViewModel
import com.aldebaran.domain.entities.ui.Artist
import com.aldebaran.domain.entities.ui.ArtistFilm
import com.aldebaran.domain.entities.ui.ArtistPict
import com.aldebaran.domain.repository.remote.ArtistRemoteSource
import com.aldebaran.event.Event
import com.aldebaran.network.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PersonViewModel @ViewModelInject constructor(
    private val remote: ArtistRemoteSource
) : BaseViewModel() {

    private val _artist by lazy { MutableLiveData<Artist>() }
    private val _artistFilm by lazy { MutableLiveData<List<ArtistFilm>>() }
    private val _artistPict by lazy { MutableLiveData<List<ArtistPict>>() }

    private val _loading by lazy { MutableLiveData(true) }
    val loading: LiveData<Boolean> get() = _loading
    private val _eventError by lazy { MutableLiveData<Event<Boolean>>() }
    val eventError: LiveData<Event<Boolean>> get() = _eventError

    val artist: LiveData<Artist> get() = _artist
    val artistFilm: LiveData<List<ArtistFilm>> get() = _artistFilm
    val artistPict: LiveData<List<ArtistPict>> get() = _artistPict

    fun getDetailPersonRequest(personId: Int){
        viewModelScope.launch {
            _loading.value = true
            val artist = async(IO) { remote.getDetailPerson(personId) }
            val pict = async(IO) { remote.getPersonPict(personId) }
            val film = async(IO) { remote.getPersonFilm(personId) }

            handleDetailArtist(artist.await())
            handleArtistFilm(film.await())
            handleArtistPict(pict.await())
        }
    }

    private fun handleDetailArtist(artist: Result<Artist>) {
        when(artist) {
            is Result.Success -> {
                _artist.value = artist.data
                _loading.value = false
            }
            is Result.Error -> {
                _eventError.value = Event(true)
                _loading.value = false
            }
        }
    }

    private fun handleArtistFilm(film: Result<List<ArtistFilm>>) {
        when (film) {
            is Result.Success -> _artistFilm.value = film.data
            is Result.Error -> Unit
        }
    }

    private fun handleArtistPict(pict: Result<List<ArtistPict>>) {
        when (pict) {
            is Result.Success -> _artistPict.value = pict.data
            is Result.Error -> Unit
        }
    }
}