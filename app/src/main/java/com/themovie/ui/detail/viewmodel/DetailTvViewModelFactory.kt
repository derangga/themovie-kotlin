package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.DetailTvRepos
import javax.inject.Inject

class DetailTvViewModelFactory
@Inject constructor(private val detailTvRepos: DetailTvRepos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DetailTvRepos::class.java).newInstance(detailTvRepos)
    }
}