package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailMvViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailLiveMovieData: MutableLiveData<FetchDetailMovieData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    companion object{
        private var filmId = 0
        fun setFilmId(filmId: Int){
            this.filmId = filmId
        }
    }

    fun getDetailMovieRequest(): MutableLiveData<FetchDetailMovieData> {
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
        return detailLiveMovieData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    override fun onCleared() {
        composite.clear()
    }
}