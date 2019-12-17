package com.themovie.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.local.LocalRepository
import javax.inject.Inject

class GenreViewModelFactory
    @Inject constructor(private val localRepository: LocalRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LocalRepository::class.java)
            .newInstance(localRepository)
    }
}