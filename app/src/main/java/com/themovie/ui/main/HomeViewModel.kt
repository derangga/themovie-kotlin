package com.themovie.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.db.*
import com.themovie.model.online.FetchMainData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.repos.local.*
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class HomeViewModel(private val apiRepository: ApiRepository, private val trendingLocalRepos: TrendingLocalRepos,
                    private val genreRepos: GenreLocalRepos, private val upcomingLocalRepos: UpcomingLocalRepos,
                    private val discoverTvLocalRepos: DiscoverTvLocalRepos,
                    private val discoverMvLocalRepos: DiscoverMvLocalRepos
) : ViewModel() {

    private val loadDataStatus by lazy { MutableLiveData<LoadDataState>() }
    private val trendingData by lazy { MediatorLiveData<List<Trending>>() }
    private val upcomingData by lazy { MediatorLiveData<List<Upcoming>>() }
    private val genreData by lazy { MediatorLiveData<List<Genre>>() }
    private val discoverTvData by lazy { MediatorLiveData<List<Tv>>() }
    private val discoverMvData by lazy { MediatorLiveData<List<Movies>>() }
    var isFirstLoad = false

    fun getDataRequest() {
        viewModelScope.launch {
            loadDataStatus.value = LoadDataState.LOADING
            apiRepository.getMainData(object: ApiCallback<FetchMainData>{
                override fun onSuccessRequest(response: FetchMainData?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    if(isFirstLoad){
                        response?.let {
                            insertDataFirstLoad(
                                it.popular?.results!!, it.upcomingResponse?.results!!,
                                it.genre?.genres!!, it.tvResponse?.results!!,
                                it.moviesResponse?.movies!!
                            )
                        }
                    } else {
                        response?.let {
                            updateLocalData(
                                it.popular?.results!!, it.upcomingResponse?.results!!,
                                it.genre?.genres!!, it.tvResponse?.results!!,
                                it.moviesResponse?.movies!!
                            )
                        }
                    }
                }

                override fun onErrorRequest(errorBody: ResponseBody?) {
                    loadDataStatus.value = LoadDataState.ERROR
                }

                override fun onFailure(e: Exception) {
                    loadDataStatus.value = LoadDataState.ERROR
                }
            })
        }
    }

    private fun insertDataFirstLoad(trendingList: List<Trending>, upcomingList: List<Upcoming>,
                                    genreList: List<Genre>, tvList: List<Tv>, movieList: List<Movies>)
    {
        viewModelScope.launch(IO) {
            trendingLocalRepos.insert(trendingList)
            upcomingLocalRepos.insert(upcomingList)
            genreRepos.insert(genreList)
            discoverTvLocalRepos.insert(tvList)
            discoverMvLocalRepos.insert(movieList)
        }
    }

    private fun updateLocalData(trendingList: List<Trending>, upcomingList: List<Upcoming>,
                                genreList: List<Genre>, tvList: List<Tv>, movieList: List<Movies>)
    {
        viewModelScope.launch(IO) {
            trendingList.forEachIndexed { index, trending ->
                val data = Trending(
                    index + 1, trending.id, trending.title,
                    trending.posterPath, trending.backdropPath,
                    trending.overview, trending.voteAverage, trending.releaseDate
                )
                trendingLocalRepos.update(data)
            }

            upcomingList.forEachIndexed { index, upcoming ->
                val data = Upcoming(
                    index + 1, upcoming.id, upcoming.title,
                    upcoming.posterPath, upcoming.backdropPath,
                    upcoming.overview, upcoming.voteAverage, upcoming.releaseDate
                )
                upcomingLocalRepos.update(data)
            }

            genreList.forEachIndexed { index, genre ->
                val data = Genre(index + 1, genre.id, genre.name)
                genreRepos.update(data)
            }

            tvList.forEachIndexed { index, tv ->
                val data = Tv(
                    index + 1, tv.id, tv.name,
                    tv.voteAverage, tv.voteCount,
                    tv.posterPath, tv.backdropPath, tv.overview
                )
                discoverTvLocalRepos.update(data)
            }

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

    fun getLocalData(){
        trendingData.addSource(trendingLocalRepos.getTrendingList()){
            trendingData.value = it
            isFirstLoad = it.isEmpty()
        }

        upcomingData.addSource(upcomingLocalRepos.getAllUpcoming()){
            upcomingData.value = it
        }

        genreData.addSource(genreRepos.getPartOfGenre()){
            genreData.value = it
        }

        discoverTvData.addSource(discoverTvLocalRepos.getDiscoverTvList()){
            discoverTvData.value = it
        }

        discoverMvData.addSource(discoverMvLocalRepos.getDiscoverMovieLis()){
            discoverMvData.value = it
        }
    }


    fun getTrendingLocalData(): LiveData<List<Trending>> = trendingData
    fun getUpcomingLocalData(): LiveData<List<Upcoming>> = upcomingData
    fun getGenreLocalData(): LiveData<List<Genre>> = genreData
    fun getDiscoverTvLocalData(): LiveData<List<Tv>> = discoverTvData
    fun getDiscoverMvLocalData(): LiveData<List<Movies>> = discoverMvData
    fun getLoadDataStatus(): LiveData<LoadDataState> = loadDataStatus
}