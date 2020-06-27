package com.themovie.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.themovie.localdb.LocalSource
import com.themovie.model.db.Genre
import javax.inject.Inject

class GenreViewModel @Inject constructor (private val localRepos: LocalSource): ViewModel() {

    val genreMovies: LiveData<List<Genre>>
        get() = localRepos.getAllGenre()
}