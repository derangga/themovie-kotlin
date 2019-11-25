package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.db.Genre
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailTvViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailTvLiveData: MutableLiveData<FetchDetailTvData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    companion object {
        private var filmId = 0
        fun setFilmId(filmId: Int){
            this.filmId = filmId
        }
    }

    fun getDetailTvRequest(): MutableLiveData<FetchDetailTvData> {
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
        return detailTvLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    override fun onCleared() {
        composite.clear()
    }
}