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
            trendingList.forEachIndexed { index, trending ->
                val data = Trending(
                    index + 1, trending.id, trending.title,
                    trending.posterPath, trending.backdropPath,
                    trending.overview, trending.voteAverage, trending.releaseDate
                )
                trendingLocalRepos.update(data)
            }
        }
    }

    fun updateLocalUpComing(upcomingList: List<Upcoming>){
        viewModelScope.launch(IO) {
            upcomingList.forEachIndexed { index, upcoming ->
                val data = Upcoming(
                    index + 1, upcoming.id, upcoming.title,
                    upcoming.posterPath, upcoming.backdropPath,
                    upcoming.overview, upcoming.voteAverage, upcoming.releaseDate
                )
                upcomingLocalRepos.update(data)
            }
        }
    }

    fun updateLocalGenre(genreList: List<Genre>){
        viewModelScope.launch(IO) {
            genreList.forEachIndexed { index, genre ->
                val data = Genre(index + 1, genre.id, genre.name)
                genreRepos.update(data)
            }
        }
    }

    fun updateLocalTv(tvList: List<Tv>){
        viewModelScope.launch(IO) {
            tvList.forEachIndexed { index, tv ->
                val data = Tv(
                    index + 1, tv.id, tv.name,
                    tv.voteAverage, tv.voteCount,
                    tv.posterPath, tv.backdropPath, tv.overview
                )
                discoverTvLocalRepos.update(data)
            }
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        viewModelScope.launch {
            movieList.forEachIndexed { index, movies ->
                val data = Movies(
                    index + 1, movies.id,
                    movies.title, movies.posterPath, movies.backdropPath,
                    movies.overview, movies.voteAverage, movies.releaseDate
                )
                discoverMvLocalRepos.update(data)
            }
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