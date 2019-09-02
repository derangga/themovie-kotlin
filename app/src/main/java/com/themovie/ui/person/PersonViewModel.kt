package com.themovie.ui.person

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
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

class PersonViewModel(application: Application) : AndroidViewModel(application) {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val personRepos: PersonRepos = PersonRepos()
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
        birthday.value = DateConverter.convert(personData.birthday)
        place.value = personData.placeOfBirth
        biography.value = personData.biography
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

    fun getPhotoUrl(): LiveData<String> {
        return photoUrl
    }

    fun getBiography(): LiveData<String> {
        return biography
    }

    companion object {
        @BindingAdapter("profilePict")
        @JvmStatic
        fun loadImage(view: ImageView, photoUrl: String?){
            Log.e("vm", photoUrl.toString())
            ImageCache.setImageViewUrl(view.context, photoUrl.toString(), view)
        }
    }

    override fun onCleared() {
        composite.clear()
    }
}