package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailTvData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailTvViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val detailTvLiveData by lazy { MutableLiveData<FetchDetailTvData>() }
    private val loadDataStatus by lazy { MutableLiveData<LoadDataState>() }

    companion object {
        private var filmId = 0
        fun setFilmId(filmId: Int){
            this.filmId = filmId
        }
    }

    fun getDetailTvRequest() {
        viewModelScope.launch {
            apiRepository.getDetailDataTv(filmId, object: ApiCallback<FetchDetailTvData>{
                override fun onSuccessRequest(response: FetchDetailTvData?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    detailTvLiveData.value = response
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

    fun setDetailTv(): MutableLiveData<FetchDetailTvData> = detailTvLiveData
    fun getLoadStatus(): MutableLiveData<LoadDataState> = loadDataStatus


}