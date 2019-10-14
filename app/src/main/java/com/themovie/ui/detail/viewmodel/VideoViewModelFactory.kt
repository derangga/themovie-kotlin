package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.VideoRepos
import javax.inject.Inject

class VideoViewModelFactory
@Inject constructor(private val videoRepos: VideoRepos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(VideoRepos::class.java).newInstance(videoRepos)
    }
}