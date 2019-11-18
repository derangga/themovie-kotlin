package com.themovie.ui.person

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.PersonResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class PersonViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val personLiveData: MutableLiveData<FetchPersonData> = MutableLiveData()
    private val loadDataState: MutableLiveData<LoadDataState> = MutableLiveData()

    // Person Data
    private val name: MutableLiveData<String> = MediatorLiveData()
    private val birthday: MutableLiveData<String> = MediatorLiveData()
    private val place: MutableLiveData<String> = MediatorLiveData()
    private val biography: MutableLiveData<String> = MediatorLiveData()
    private val photoUrl: MutableLiveData<String> = MediatorLiveData()

    companion object{
        private var personId = 0
        fun setPersonId(personId: Int){
            this.personId = personId
        }
    }

    fun getPersonData(): MutableLiveData<FetchPersonData> {
        viewModelScope.launch {
            apiRepository.getPersonData(personId, object: ApiCallback<FetchPersonData>{
                override fun onSuccessRequest(response: FetchPersonData?) {
                    loadDataState.value = LoadDataState.LOADED
                    personLiveData.value = response
                }

                override fun onErrorRequest(errorBody: ResponseBody?) {
                    loadDataState.value = LoadDataState.ERROR
                }

                override fun onFailure(e: Exception) {
                    loadDataState.value = LoadDataState.ERROR
                }
            })
        }
        return  personLiveData
    }

    fun getLoadStatus(): MutableLiveData<LoadDataState> {
        return loadDataState
    }

    fun setPersonData(personData: PersonResponse){
        Log.e("vm", personData.name)
        name.value = personData.name
        birthday.value = if(personData.birthday != null) personData.birthday.convertDate() else ""
        place.value = personData.placeOfBirth ?: ""
        biography.value = personData.biography ?: ""
        photoUrl.value = ApiUrl.IMG_POSTER + personData.profilePath.toString()
    }

    fun getName(): LiveData<String> {
        return name
    }

    fun getBirthday(): LiveData<String> {
        return birthday
    }

    fun getPlace(): LiveData<String> {
        return place
    }

    fun getBiography(): LiveData<String> {
        return biography
    }

    fun getPhotoUrl(): LiveData<String> {
        return photoUrl
    }

    override fun onCleared() {
        composite.clear()
    }
}