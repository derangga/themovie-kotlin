package com.themovie.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.themovie.model.db.Genre
import com.themovie.repos.local.GenreLocalRepos

class GenreViewModel(private val genreLocalRepos: GenreLocalRepos): ViewModel() {

    fun getGenreList(): LiveData<List<Genre>> = genreLocalRepos.getAllGenre()
}