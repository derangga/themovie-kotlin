package com.themovie.ui.person

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.themovie.helper.DateConverter
import com.themovie.helper.ImageCache
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchPersonData
import com.themovie.model.online.person.PersonResponse
import com.themovie.repos.fromapi.PersonRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

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

    fun getPersonData(token: String, personId: Int): MutableLiveData<FetchPersonData> {
        composite.add(
            personRepos.getPersonData(token, personId).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<FetchPersonData>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: FetchPersonData) {
                        personLiveData.value = t
                        loadDataState.value = LoadDataState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        loadDataState.value = LoadDataState.ERROR
                    }
                })
        )
        return  personLiveData
    }

    fun getLoadStatus(): MutableLiveData<LoadDataState> {
        return loadDataState
    }

    fun setPersonData(personData: PersonResponse){
        Log.e("vm", personData.name)
        name.value = personData.name
        birthday.value = if(personData.birthday != null) DateConverter.convert(personData.birthday) else ""
        place.value = if(personData.placeOfBirth != null) personData.placeOfBirth else ""
        biography.value = if(personData.biography != null) personData.biography else ""
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