package com.themovie.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.local.GenreLocalRepos
import javax.inject.Inject

class GenreViewModelFactory
    @Inject constructor(private val genreLocalRepos: GenreLocalRepos) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GenreLocalRepos::class.java).newInstance(genreLocalRepos)
    }
}