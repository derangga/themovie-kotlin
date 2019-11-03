package com.themovie.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.DateConverter
import com.themovie.helper.LoadDataState
import com.themovie.model.local.*
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.discovermv.Movies
import com.themovie.model.online.discovertv.Tv
import com.themovie.model.online.genre.Genre
import com.themovie.repos.fromapi.MainRepos
import com.themovie.repos.local.*
import com.themovie.restapi.ApiUrl
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
        trendingList.forEachIndexed { i, movies ->
            val trending = Trending( i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = movies.releaseDate,
                rating = movies.voteAverage,
                posterPath = movies.posterPath,
                backDropPath = movies.backdropPath
            )
            viewModelScope.launch {
                trendingLocalRepos.insert(trending)
            }
        }
    }

    fun insertLocalUpcoming(upcomingList: List<Movies>){
        upcomingList.forEachIndexed { i, movies ->
            val upcoming = Upcoming(i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = DateConverter.convert(movies.releaseDate),
                posterPath = movies.posterPath.toString(),
                backDropPath = movies.backdropPath.toString(),
                rating = movies.voteAverage
            )
            viewModelScope.launch {
                upcomingLocalRepos.insert(upcoming)
            }
        }
    }

    fun insertLocalGenre(genreList: List<Genre>){
        genreList.forEachIndexed { index, genre ->
            val genreData = GenreLocal(
                index,
                genreId = genre.id,
                name = genre.name
            )
            viewModelScope.launch {
                genreRepos.insert(genreData)
            }
        }
    }

    fun insertLocalTv(tvList: List<Tv>){
        tvList.forEachIndexed { i, tv ->
            val tvData = TvLocal( i,
                tvId = tv.id,
                title = tv.name,
                rating = tv.voteAverage,
                posterPath = tv.posterPath.toString(),
                backDropPath = tv.backdropPath.toString()
            )
            viewModelScope.launch {
                discoverTvLocalRepos.insert(tvData)
            }
        }
    }

    fun insertLocalMovies(movieList: List<Movies>){
        movieList.forEachIndexed { i, movies ->
            val movieData = MoviesLocal( i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = DateConverter.convert(movies.releaseDate),
                rating = movies.voteAverage,
                posterPath = movies.posterPath.toString(),
                backDropPath = movies.backdropPath.toString()
            )
            viewModelScope.launch {
                discoverMvLocalRepos.insert(movieData)
            }
        }
    }

    fun updateLocalTrending(trendingList: List<Movies>){
        trendingList.forEachIndexed { i, movies ->
            val trending = Trending( i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = movies.releaseDate,
                rating = movies.voteAverage,
                posterPath = movies.posterPath,
                backDropPath = movies.backdropPath
            )
            viewModelScope.launch {
                trendingLocalRepos.update(trending)
            }
        }
    }

    fun updateLocalUpComing(upcomingList: List<Movies>){
        upcomingList.forEachIndexed { i, movies ->
            val upcoming = Upcoming(i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = DateConverter.convert(movies.releaseDate),
                posterPath = movies.posterPath.toString(),
                backDropPath = movies.backdropPath.toString(),
                rating = movies.voteAverage
            )
            viewModelScope.launch {
                upcomingLocalRepos.update(upcoming)
            }
        }
    }

    fun updateLocalGenre(genreList: List<Genre>){
        genreList.forEachIndexed { index, genre ->
            val genreData = GenreLocal(
                index,
                genreId = genre.id,
                name = genre.name
            )
            viewModelScope.launch {
                genreRepos.insert(genreData)
            }
        }
    }

    fun updateLocalTv(tvList: List<Tv>){
        tvList.forEachIndexed { i, tv ->
            val tvData = TvLocal( i,
                tvId = tv.id,
                title = tv.name,
                rating = tv.voteAverage,
                posterPath = tv.posterPath.toString(),
                backDropPath = tv.backdropPath.toString()
            )
            viewModelScope.launch {
                discoverTvLocalRepos.update(tvData)
            }
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        movieList.forEachIndexed { i, movies ->
            val movieData = MoviesLocal( i,
                mvId = movies.id,
                title = movies.title,
                dateRelease = DateConverter.convert(movies.releaseDate),
                rating = movies.voteAverage,
                posterPath = movies.posterPath.toString(),
                backDropPath = movies.backdropPath.toString()
            )
            viewModelScope.launch {
                discoverMvLocalRepos.update(movieData)
            }
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