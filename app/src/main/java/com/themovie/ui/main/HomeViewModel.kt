package com.themovie.ui.main

import android.util.Log
import androidx.core.util.rangeTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.local.*
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.discovermv.Movies
import com.themovie.model.online.discovertv.Tv
import com.themovie.model.online.genre.Genre
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

    private val onlineLiveDataFetch: MutableLiveData<FetchMainData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

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

    fun insertLocalTrending(trendingList: List<Movies>){
        val trendingLocal = trendingList.map { trending ->
            Trending(
                mvId = trending.id,
                title = trending.title,
                dateRelease = trending.releaseDate.convertDate(),
                rating = trending.voteAverage,
                posterPath = trending.posterPath,
                backDropPath = trending.backdropPath
            )
        }
        viewModelScope.launch(IO) {
            trendingLocalRepos.insert(trendingLocal)
        }
    }

    fun insertLocalUpcoming(upcomingList: List<Movies>){
        val upcomingLocal = upcomingList.map { upcoming->
            Upcoming(
                mvId = upcoming.id,
                title = upcoming.title,
                dateRelease = upcoming.releaseDate.convertDate(),
                posterPath = upcoming.posterPath,
                backDropPath = upcoming.backdropPath,
                rating = upcoming.voteAverage
            )
        }
        viewModelScope.launch(IO) {
            upcomingLocalRepos.insert(upcomingLocal)
        }
    }

    fun insertLocalGenre(genreList: List<Genre>){
        val genreLocal = genreList.map { genre ->
            GenreLocal(genreId = genre.id, name = genre.name)
        }
        viewModelScope.launch(IO) {
            genreRepos.insert(genreLocal)
        }
    }

    fun insertLocalTv(tvList: List<Tv>){
        val tvLocal = tvList.map { tv ->
            TvLocal(
                tvId = tv.id,
                title = tv.name,
                rating = tv.voteAverage,
                posterPath = tv.posterPath,
                backDropPath = tv.backdropPath
            )
        }
        viewModelScope.launch(IO) {
            discoverTvLocalRepos.insert(tvLocal)
        }
    }

    fun insertLocalMovies(movieList: List<Movies>){
        val moviesLocal = movieList.map { movies ->
            MoviesLocal(
                mvId = movies.id,
                title = movies.title,
                dateRelease = movies.releaseDate.convertDate(),
                rating = movies.voteAverage,
                posterPath = movies.posterPath.orEmpty(),
                backDropPath = movies.posterPath.orEmpty()
            )
        }
        viewModelScope.launch {
            discoverMvLocalRepos.insert(moviesLocal)
        }
    }

    fun updateLocalTrending(trendingList: List<Movies>){
        val trendingLocal = trendingList.map { trending ->
            Trending(
                mvId = trending.id,
                title = trending.title,
                dateRelease = trending.releaseDate.convertDate(),
                rating = trending.voteAverage,
                posterPath = trending.posterPath,
                backDropPath = trending.backdropPath
            )
        }
        viewModelScope.launch(IO) {
            trendingLocalRepos.update(trendingLocal)
        }
    }

    fun updateLocalUpComing(upcomingList: List<Movies>){
        val upcomingLocal = upcomingList.map { upcoming->
            Upcoming(
                mvId = upcoming.id,
                title = upcoming.title,
                dateRelease = upcoming.releaseDate.convertDate(),
                posterPath = upcoming.posterPath,
                backDropPath = upcoming.backdropPath,
                rating = upcoming.voteAverage
            )
        }
        viewModelScope.launch(IO) {
            upcomingLocalRepos.update(upcomingLocal)
        }
    }

    fun updateLocalGenre(genreList: List<Genre>){
        val genreLocal = genreList.map { genre ->
            GenreLocal(genreId = genre.id, name = genre.name)
        }
        viewModelScope.launch(IO) {
            genreRepos.update(genreLocal)
        }
    }

    fun updateLocalTv(tvList: List<Tv>){
        val tvLocal = tvList.map { tv ->
            TvLocal(
                tvId = tv.id,
                title = tv.name,
                rating = tv.voteAverage,
                posterPath = tv.posterPath,
                backDropPath = tv.backdropPath
            )
        }
        viewModelScope.launch(IO) {
            discoverTvLocalRepos.update(tvLocal)
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        val moviesLocal = movieList.map { movies ->
            MoviesLocal(
                mvId = movies.id,
                title = movies.title,
                dateRelease = movies.releaseDate.convertDate(),
                rating = movies.voteAverage,
                posterPath = movies.posterPath.orEmpty(),
                backDropPath = movies.posterPath.orEmpty()
            )
        }
        viewModelScope.launch {
            discoverMvLocalRepos.update(moviesLocal)
        }
    }

    fun getTrendingLocalData(): LiveData<List<Trending>>{
        return trendingLocalRepos.getTrendingList()
    }

    fun getUpcomingLocalData(): LiveData<List<Upcoming>>{
        return upcomingLocalRepos.getAllUpcoming()
    }

    fun getDiscoverTvLocalData(): LiveData<List<TvLocal>>{
        return discoverTvLocalRepos.getDiscoverTvList()
    }

    fun getDiscoverMvLocalData(): LiveData<List<MoviesLocal>>{
        return discoverMvLocalRepos.getDiscoverMovieLis()
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState>{
        return loadDataStatus
    }

    fun getGenreLocalData(): LiveData<List<GenreLocal>>{
        return genreRepos.getPartOfGenre()
    }
}