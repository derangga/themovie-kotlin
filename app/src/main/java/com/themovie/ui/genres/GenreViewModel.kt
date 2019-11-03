package com.themovie.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.themovie.model.local.GenreLocal
import com.themovie.repos.local.GenreLocalRepos

class GenreViewModel(private val genreLocalRepos: GenreLocalRepos): ViewModel() {

    fun getGenreList(): LiveData<List<GenreLocal>> = genreLocalRepos.getAllGenre()
}