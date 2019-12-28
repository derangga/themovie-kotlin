package com.themovie.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.themovie.model.db.Genre
import com.themovie.repos.local.LocalRepository

class GenreViewModel(private val localRepos: LocalRepository): ViewModel() {

    fun getGenreList(): LiveData<List<Genre>> = localRepos.getAllGenre()
}