package com.themovie.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.ApiRepository
import javax.inject.Inject

class SuggestTvFactory
    @Inject constructor(private val apiRepository: ApiRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ApiRepository::class.java)
            .newInstance(apiRepository)
    }
}