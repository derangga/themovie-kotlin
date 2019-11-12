package com.themovie.ui.person

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.PersonResponse
import com.themovie.repos.fromapi.PersonRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class PersonViewModel(private val personRepos: PersonRepos) : ViewModel() {

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

    init {
        viewModelScope.launch {
            try {
                val response = personRepos.getPersonData(ApiUrl.TOKEN, personId)
                if(response != null){
                    personLiveData.value = response
                    loadDataState.value = LoadDataState.LOADED
                } else loadDataState.value = LoadDataState.ERROR

            } catch (e: Exception){
                loadDataState.value = LoadDataState.ERROR
            }
        }
    }

    fun getPersonData(): MutableLiveData<FetchPersonData> {
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