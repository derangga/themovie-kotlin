package com.themovie.ui.genres

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.aldebaran.domain.entities.mapper.toGenre
import com.aldebaran.domain.entities.ui.Genre
import com.aldebaran.domain.repository.local.GenreLocalSource
import kotlinx.coroutines.Dispatchers.IO

class GenreViewModel @ViewModelInject constructor (private val genreLocalSource: GenreLocalSource): ViewModel() {

    val genreMovies: LiveData<List<Genre>> by lazy {
        liveData(IO) {
            val genre = genreLocalSource.getAll().map { it.toGenre() }
            emit(genre)
        }
    }
}