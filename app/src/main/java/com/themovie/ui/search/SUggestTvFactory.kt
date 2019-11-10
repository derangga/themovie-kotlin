package com.themovie.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.search.SearchRepos
import javax.inject.Inject

class SUggestTvFactory
    @Inject constructor(private val searchRepos: SearchRepos)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchRepos::class.java).newInstance(searchRepos)
    }
}