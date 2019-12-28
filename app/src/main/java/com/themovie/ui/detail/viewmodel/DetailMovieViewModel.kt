package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailMovieViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val detailLiveMovieData by lazy { MutableLiveData<FetchDetailMovieData>() }
    private val loadDataStatus by lazy { MutableLiveData<LoadDataState>() }

    companion object{
        private var filmId = 0
        fun setFilmId(filmId: Int){
            this.filmId = filmId
        }
    }

    fun getDetailMovieRequest(){
        viewModelScope.launch {
            apiRepository.getDetailDataMovie(filmId, object: ApiCallback<FetchDetailMovieData>{
                override fun onSuccessRequest(response: FetchDetailMovieData?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    detailLiveMovieData.value = response
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

    fun setDetailMovie(): MutableLiveData<FetchDetailMovieData> = detailLiveMovieData
    fun getLoadStatus(): MutableLiveData<LoadDataState> = loadDataStatus
}