package com.themovie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.repos.local.*
import javax.inject.Inject

class HomeViewFactory
@Inject constructor(
    private val apiRepository: ApiRepository,
    private val localRepository: LocalRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ApiRepository::class.java, LocalRepository::class.java
        ).newInstance(apiRepository, localRepository)
    }
}