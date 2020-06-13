package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailTvData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject
import kotlin.Exception

class DetailTvViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val detailTvLiveData by lazy { MutableLiveData<FetchDetailTvData>() }
    private val loadDataStatus by lazy { MutableLiveData<LoadDataState>() }

    fun getDetailTvRequest(filmId: Int) {
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