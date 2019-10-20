package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.video.VideoResponse
import com.themovie.repos.fromapi.VideoRepos
import com.themovie.restapi.ApiUrl
import kotlinx.coroutines.launch

class VideoViewModel(private val videoRepos: VideoRepos) : ViewModel() {

    private val videoLiveData: MutableLiveData<VideoResponse> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    companion object{
        private var filmId = 0
        private var type = ""
        fun setFilmIdAndType(filmId: Int, type: String){
            this.filmId = filmId
            this.type = type
        }
    }

    init {
        if(type == Constant.MOVIE){
            viewModelScope.launch {
                try {
                    val response = videoRepos.getMovieVideo(filmId, ApiUrl.TOKEN)
                    if(response.isSuccessful){
                        videoLiveData.value = response.body()
                        loadDataStatus.value = LoadDataState.LOADED
                    } else loadDataStatus.value = LoadDataState.ERROR

                } catch (e: Exception){
                    loadDataStatus.value = LoadDataState.ERROR
                }
            }
        } else if(type == Constant.TV){
            viewModelScope.launch {
                try {
                    val response = videoRepos.getTvVideo(filmId, ApiUrl.TOKEN)
                    if(response.isSuccessful){
                        videoLiveData.value = response.body()
                        loadDataStatus.value = LoadDataState.LOADED
                    } else loadDataStatus.value = LoadDataState.ERROR

                } catch (e: Exception){
                    loadDataStatus.value = LoadDataState.ERROR
                }
            }
        }
    }

    fun getVideoMovie(): MutableLiveData<VideoResponse> {
        return videoLiveData
    }

    fun getVideoTv(filmId: Int): MutableLiveData<VideoResponse> {

        return  videoLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }
}