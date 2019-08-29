package com.themovie.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovie.helper.LoadDataState
import com.themovie.model.local.MoviesLocal
import com.themovie.model.local.Trending
import com.themovie.model.local.TvLocal
import com.themovie.model.local.Upcoming
import com.themovie.model.online.MainData
import com.themovie.model.online.discovermv.Movies
import com.themovie.model.online.discovertv.Tv
import com.themovie.repos.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val mainRepos: MainRepos = MainRepos()
    private val upcomingLocalRepos: UpcomingLocalRepos = UpcomingLocalRepos(application)
    private val trendingLocalRepos: TrendingLocalRepos = TrendingLocalRepos(application)
    private val discoverTvLocalRepos: DiscoverTvLocalRepos = DiscoverTvLocalRepos(application)
    private val discoverMvLocalRepos: DiscoverMvLocalRepos = DiscoverMvLocalRepos(application)
    private val onlineLiveData: MutableLiveData<MainData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    fun getDataRequest(token: String): MutableLiveData<MainData>{
        composite.add(
            mainRepos.getDataMovide(token).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<MainData>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: MainData) {
                        onlineLiveData.value = t
                        loadDataStatus.value = LoadDataState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        loadDataStatus.value = LoadDataState.ERROR
                    }
                })
        )
        return onlineLiveData
    }

    fun insertLocalTrending(trendingList: List<Tv>){
        for(i in trendingList.indices){
            val trending = Trending( i,
                tvId = trendingList[i].id,
                title = trendingList[i].name,
                backDropPath = trendingList[i].backdropPath.toString()
            )
            trendingLocalRepos.insert(trending)
        }
    }

    fun insertLocalUpcoming(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = upcomingList[i].releaseDate,
                posterPath = upcomingList[i].posterPath.toString(),
                backDropPath = upcomingList[i].backdropPath.toString()
            )
            upcomingLocalRepos.insert(upcoming)
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
            discoverTvLocalRepos.insert(tv)
        }
    }

    fun insertLocalMovies(movieList: List<Movies>){
        for(i in movieList.indices){
           val movies = MoviesLocal( i,
               mvId = movieList[i].id,
               title = movieList[i].title,
               dateRelease = movieList[i].releaseDate,
               rating = movieList[i].voteAverage,
               posterPath = movieList[i].posterPath.toString(),
               backDropPath = movieList[i].backdropPath.toString()
           )
           discoverMvLocalRepos.insert(movies)
        }
    }

    fun updateLocalTrending(trendingList: List<Tv>){
        for(i in trendingList.indices){
            val trending = Trending( i,
                tvId = trendingList[i].id,
                title = trendingList[i].name,
                backDropPath = trendingList[i].backdropPath.toString()
            )
            trendingLocalRepos.update(trending)
        }
    }

    fun updateLocalUpComing(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = upcomingList[i].releaseDate,
                posterPath = upcomingList[i].posterPath.toString(),
                backDropPath = upcomingList[i].backdropPath.toString()
            )
            upcomingLocalRepos.update(upcoming)
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
            discoverTvLocalRepos.update(tv)
        }
    }

    fun updateLocalMovies(movieList: List<Movies>){
        for(i in movieList.indices){
            val movies = MoviesLocal( i,
                mvId = movieList[i].id,
                title = movieList[i].title,
                dateRelease = movieList[i].releaseDate,
                rating = movieList[i].voteAverage,
                posterPath = movieList[i].posterPath.toString(),
                backDropPath = movieList[i].backdropPath.toString()
            )
            discoverMvLocalRepos.update(movies)
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