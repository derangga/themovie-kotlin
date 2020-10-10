package com.themovie.ui.genres

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.repository.local.GenreLocalSource

class GenreViewModel @ViewModelInject constructor (private val genreLocalSource: GenreLocalSource): ViewModel() {

    val genreMovies: LiveData<List<GenreEntity>>
        get() = genreLocalSource.getAllGenre()
}