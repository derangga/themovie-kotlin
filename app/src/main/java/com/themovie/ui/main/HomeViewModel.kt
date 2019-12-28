package com.themovie.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.db.*
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.repos.local.*
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class HomeViewModel(
    private val apiRepository: ApiRepository,
    private val localRepos: LocalRepository
) : ViewModel() {

    private val loadDataStatus by lazy { MutableLiveData<LoadDataState>() }

    fun getDataRequest() {
        viewModelScope.launch {
            loadDataStatus.value = LoadDataState.LOADING
            apiRepository.getMainData(object: ApiCallback<LoadDataState>{
                override fun onSuccessRequest(response: LoadDataState?) {
                    loadDataStatus.value = response
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

    suspend fun isFirstLoad(): Boolean {
        var firstLoadStatus = true
        val trendingRows = localRepos.getTrendingRows()
        val upcomingRows = localRepos.getUpcomingRows()
        val genreRows = localRepos.getGenreRows()
        val tvRows = localRepos.getTvRows()
        val movieRows = localRepos.getMoviesRows()
        if(trendingRows != 0 && upcomingRows != 0 &&
            genreRows != 0 && tvRows != 0 &&
            movieRows != 0){
            firstLoadStatus = false
        }

        return firstLoadStatus
    }

    fun getTrendingLocalData(): LiveData<List<Trending>> = localRepos.getAllTrending()
    fun getUpcomingLocalData(): LiveData<List<Upcoming>> = localRepos.getAllUpcoming()
    fun getGenreLocalData(): LiveData<List<Genre>> = localRepos.getPartOfGenre()
    fun getDiscoverTvLocalData(): LiveData<List<Tv>> = localRepos.getAllDiscoverTv()
    fun getDiscoverMvLocalData(): LiveData<List<Movies>> = localRepos.getAllDiscoverMovie()
    fun getLoadDataStatus(): LiveData<LoadDataState> = loadDataStatus
}