package com.themovie.tmdb.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.themovie.datasource.entities.mapper.toGenre
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.repository.local.GenreLocalSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val genreLocalSource: GenreLocalSource): ViewModel() {

    val genreMovies: LiveData<List<Genre>> by lazy {
        liveData(IO) {
            val genre = genreLocalSource.getAll().map { it.toGenre() }
            emit(genre)
        }
    }
}