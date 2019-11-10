package com.themovie.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.db.*
import com.themovie.model.online.FetchMainData
import com.themovie.repos.fromapi.MainRepos
import com.themovie.repos.local.*
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepos: MainRepos, private val trendingLocalRepos: TrendingLocalRepos,
                    private val genreRepos: GenreLocalRepos, private val upcomingLocalRepos: UpcomingLocalRepos,
                    private val discoverTvLocalRepos: DiscoverTvLocalRepos,
                    private val discoverMvLocalRepos: DiscoverMvLocalRepos
) : ViewModel() {

    private val onlineLiveDataFetch = MutableLiveData<FetchMainData>()
    private val loadDataStatus = MutableLiveData<LoadDataState>()

    fun getDataRequest(): MutableLiveData<FetchMainData>{

        viewModelScope.launch {
            try {
                val response = mainRepos.getDataMovie(ApiUrl.TOKEN)
                if(response != null){
                    onlineLiveDataFetch.value = response
                } else loadDataStatus.value = LoadDataState.ERROR
            } catch (e: Exception){
                loadDataStatus.value = LoadDataState.ERROR
            }
        }
        return onlineLiveDataFetch
    }

    fun insertLocalTrending(trendingList: List<Trending>){
        viewModelScope.launch(IO) {
            trendingLocalRepos.insert(trendingList)
        }
    }

    fun insertLocalUpcoming(upcomingList: List<Upcoming>){
        viewModelScope.launch(IO) {
            upcomingLocalRepos.insert(upcomingList)
        }
    }

    fun insertLocalGenre(genreList: List<Genre>){
        viewModelScope.launch(IO) {
            genreRepos.insert(genreList)
        }
    }

    fun insertLocalTv(tvList: List<Tv>){
        viewModelScope.launch(IO) {
            discoverTvLocalRepos.insert(tvList)
        }
    }

    fun insertLocalMovies(movieList: List<Movies>){
        viewModelScope.launch {
            discoverMvLocalRepos.insert(movieList)
        }
    }

    fun updateLocalTrending(trendingList: List<Trending>){
        viewModelScope.launch(IO) {
            trendingLocalRepos.update(trendingList)
        }
    }

    fun updateLocalUpComing(upcomingList: List<Upcoming>){
        viewModelScope.launch(IO) {
            upcomingLocalRepos.update(upcomingList)
        }
    }

    fun updateLocalGenre(genreList: List<Genre>){
        viewModelScope.launch(IO) {
            genreRepos.update(genreList)
        }
    }

    fun updateLocalTv(tvList: List<Tv>){
        viewModelScope.launch(IO) {
            discoverTvLocalRepos.update(tvList)
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        viewModelScope.launch {
            discoverMvLocalRepos.update(movieList)
        }
    }

    fun getTrendingLocalData(): LiveData<List<Trending>>{
        return trendingLocalRepos.getTrendingList()
    }

    fun getUpcomingLocalData(): LiveData<List<Upcoming>>{
        return upcomingLocalRepos.getAllUpcoming()
    }

    fun getDiscoverTvLocalData(): LiveData<List<Tv>>{
        return discoverTvLocalRepos.getDiscoverTvList()
    }

    fun getDiscoverMvLocalData(): LiveData<List<Movies>>{
        return discoverMvLocalRepos.getDiscoverMovieLis()
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState>{
        return loadDataStatus
    }

    fun getGenreLocalData(): LiveData<List<Genre>>{
        return genreRepos.getPartOfGenre()
    }
}