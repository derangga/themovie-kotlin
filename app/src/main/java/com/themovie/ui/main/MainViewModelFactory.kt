package com.themovie.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.MainRepos
import javax.inject.Inject

class MainViewModelFactory
@Inject constructor(private val mainRepos: MainRepos, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MainRepos::class.java, Application::class.java)
            .newInstance(mainRepos, application)
    }
}