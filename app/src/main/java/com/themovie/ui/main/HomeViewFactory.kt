package com.themovie.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themovie.repos.fromapi.MainRepos
import com.themovie.repos.local.*
import javax.inject.Inject

class HomeViewFactory
@Inject constructor(private val mainRepos: MainRepos, private val trendingLocalRepos: TrendingLocalRepos,
                    private val genreLocalRepos: GenreLocalRepos,
                    private val upcomingLocalRepos: UpcomingLocalRepos, private val discoverTvLocalRepos: DiscoverTvLocalRepos,
                    private val discoverMvLocalRepos: DiscoverMvLocalRepos): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            MainRepos::class.java, TrendingLocalRepos::class.java,
            GenreLocalRepos::class.java,
            UpcomingLocalRepos::class.java, DiscoverTvLocalRepos::class.java,
            DiscoverMvLocalRepos::class.java
        ).newInstance(
            mainRepos, trendingLocalRepos, genreLocalRepos, upcomingLocalRepos, discoverTvLocalRepos, discoverMvLocalRepos
        )
    }
}