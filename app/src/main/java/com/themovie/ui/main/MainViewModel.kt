package com.themovie.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.DateConverter
import com.themovie.helper.LoadDataState
import com.themovie.model.local.MoviesLocal
import com.themovie.model.local.Trending
import com.themovie.model.local.TvLocal
import com.themovie.model.local.Upcoming
import com.themovie.model.online.FetchMainData
import com.themovie.model.online.discovermv.Movies
import com.themovie.model.online.discovertv.Tv
import com.themovie.repos.fromapi.MainRepos
import com.themovie.repos.local.DiscoverMvLocalRepos
import com.themovie.repos.local.DiscoverTvLocalRepos
import com.themovie.repos.local.TrendingLocalRepos
import com.themovie.repos.local.UpcomingLocalRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepos: MainRepos, private val trendingLocalRepos: TrendingLocalRepos,
                    private val upcomingLocalRepos: UpcomingLocalRepos, private val discoverTvLocalRepos: DiscoverTvLocalRepos,
                    private val discoverMvLocalRepos: DiscoverMvLocalRepos) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val onlineLiveDataFetch: MutableLiveData<FetchMainData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    fun getDataRequest(): MutableLiveData<FetchMainData>{
        viewModelScope.launch {
            try {
                val response = mainRepos.getDataMovie(ApiUrl.TOKEN)
                if(response != null){
                    Log.e("Success", "")
                    onlineLiveDataFetch.value = response
                } else loadDataStatus.value = LoadDataState.ERROR
            } catch (e: Exception){
                Log.e("Fail", "")
                loadDataStatus.value = LoadDataState.ERROR
            }
        }
        return onlineLiveDataFetch
    }

    fun insertLocalTrending(trendingList: List<Tv>){
        for(i in trendingList.indices){
            val trending = Trending( i,
                tvId = trendingList[i].id,
                title = trendingList[i].name,
                backDropPath = trendingList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                trendingLocalRepos.insert(trending)
            }
        }
    }

    fun insertLocalUpcoming(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = DateConverter.convert(upcomingList[i].releaseDate),
                posterPath = upcomingList[i].posterPath.toString(),
                backDropPath = upcomingList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                upcomingLocalRepos.insert(upcoming)
            }
        }
    }

    fun insertLocalTv(tvList: List<Tv>){
        for(i in tvList.indices){
            val tv = TvLocal( i,
                tvId = tvList[i].id,
                title = tvList[i].name,
                rating = tvList[i].voteAverage,
                posterPath = tvList[i].posterPath.toString(),
                backDropPath = tvList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                discoverTvLocalRepos.insert(tv)
            }
        }
    }

    fun insertLocalMovies(movieList: List<Movies>){
        for(i in movieList.indices){
           val movies = MoviesLocal( i,
               mvId = movieList[i].id,
               title = movieList[i].title,
               dateRelease = DateConverter.convert(movieList[i].releaseDate),
               rating = movieList[i].voteAverage,
               posterPath = movieList[i].posterPath.toString(),
               backDropPath = movieList[i].backdropPath.toString()
           )
            viewModelScope.launch {
                discoverMvLocalRepos.insert(movies)
            }
        }
    }

    fun updateLocalTrending(trendingList: List<Tv>){
        for(i in trendingList.indices){
            val trending = Trending( i,
                tvId = trendingList[i].id,
                title = trendingList[i].name,
                backDropPath = trendingList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                trendingLocalRepos.update(trending)
            }
        }
    }

    fun updateLocalUpComing(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = DateConverter.convert(upcomingList[i].releaseDate),
                posterPath = upcomingList[i].posterPath.toString(),
                backDropPath = upcomingList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                upcomingLocalRepos.update(upcoming)
            }
        }
    }

    fun updateLocalTv(tvList: List<Tv>){
        for(i in tvList.indices){
            val tv = TvLocal( i,
                tvId = tvList[i].id,
                title = tvList[i].name,
                rating = tvList[i].voteAverage,
                posterPath = tvList[i].posterPath.toString(),
                backDropPath = tvList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                discoverTvLocalRepos.update(tv)
            }
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        for(i in movieList.indices){
            val movies = MoviesLocal( i,
                mvId = movieList[i].id,
                title = movieList[i].title,
                dateRelease = DateConverter.convert(movieList[i].releaseDate),
                rating = movieList[i].voteAverage,
                posterPath = movieList[i].posterPath.toString(),
                backDropPath = movieList[i].backdropPath.toString()
            )
            viewModelScope.launch {
                discoverMvLocalRepos.update(movies)
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

    override fun onCleared() {
        composite.clear()
    }
}