package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.DetailMovieRepos
import javax.inject.Inject

class DetailMovieViewModelFactory
@Inject constructor(private val detailMovieRepos: DetailMovieRepos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DetailMovieRepos::class.java).newInstance(detailMovieRepos)
    }
}