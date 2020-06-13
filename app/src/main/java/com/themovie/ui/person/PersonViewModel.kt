package com.themovie.ui.person

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchPersonData
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class PersonViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val personAndFilmData by lazy { MutableLiveData<FetchPersonData>() }
    private val loadDataState by lazy { MutableLiveData<LoadDataState>() }

    companion object{
        private var personId = 0
        fun setPersonId(personId: Int){
            this.personId = personId
        }
    }

    fun getPersonData(){
        viewModelScope.launch {
            apiRepository.getPersonData(personId, object: ApiCallback<FetchPersonData>{
                override fun onSuccessRequest(response: FetchPersonData?) {
                    loadDataState.value = LoadDataState.LOADED
                    personAndFilmData.value = response
                }

                override fun onErrorRequest(errorBody: ResponseBody?) {
                    loadDataState.value = LoadDataState.ERROR
                }

                override fun onFailure(e: Exception) {
                    loadDataState.value = LoadDataState.ERROR
                }
            })
        }
    }

    fun setPersonData(): MutableLiveData<FetchPersonData> = personAndFilmData
    fun getLoadStatus(): MutableLiveData<LoadDataState> = loadDataState
}