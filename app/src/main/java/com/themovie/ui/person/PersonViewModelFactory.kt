package com.themovie.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.PersonRepos
import javax.inject.Inject

class PersonViewModelFactory
@Inject constructor(private val personRepos: PersonRepos): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PersonRepos::class.java).newInstance(personRepos)
    }
}