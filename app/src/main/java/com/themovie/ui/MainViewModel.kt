package com.themovie.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themovie.model.local.Upcoming
import com.themovie.model.online.MainData
import com.themovie.model.online.Movies
import com.themovie.repos.MainRepos
import com.themovie.repos.UpcomingLocalRepos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val mainRepos: MainRepos = MainRepos()
    private val upcomingLocalRepos: UpcomingLocalRepos = UpcomingLocalRepos(application)
    private val onlineLiveData: MutableLiveData<MainData> = MutableLiveData()

    fun getDataRequest(token: String): MutableLiveData<MainData>{
        composite.add(
            mainRepos.getDataMovide(token).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<MainData>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: MainData) {
                        onlineLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.e("viewModel", e.message.toString())
                    }
                })
        )
        return onlineLiveData
    }

    fun insertLocalUpcoming(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = upcomingList[i].releaseDate,
                imgPath = upcomingList[i].backdropPath.toString()
            )
            upcomingLocalRepos.insert(upcoming)
            Log.e("tag", upcomingList[i].backdropPath.toString())
        }
    }

    fun updateLocalUpComing(upcomingList: List<Movies>){
        for(i in upcomingList.indices){
            val upcoming = Upcoming(i,
                mvId = upcomingList[i].id,
                title = upcomingList[i].title,
                dateRelease = upcomingList[i].releaseDate,
                imgPath = upcomingList[i].backdropPath.toString()
            )
            upcomingLocalRepos.update(upcoming)
        }
    }

    fun getUpcomingLocalData(): LiveData<List<Upcoming>>{
        return upcomingLocalRepos.getAllUpcoming()
    }

    override fun onCleared() {
        composite.clear()
    }
}