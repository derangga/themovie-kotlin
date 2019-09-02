package com.themovie.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.themovie.helper.LoadDataState
import com.themovie.model.online.video.VideoResponse
import com.themovie.repos.fromapi.VideoRepos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val videoRepos: VideoRepos = VideoRepos()
    private val videoLiveData: MutableLiveData<VideoResponse> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    fun getVideoMovie(movieId: Int, token: String): MutableLiveData<VideoResponse> {
        composite.add(
            videoRepos.getMovieVideo(movieId, token).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<VideoResponse>(){
                    override fun onSuccess(t: VideoResponse) {
                        videoLiveData.value = t
                        loadDataStatus.value = LoadDataState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        loadDataStatus.value = LoadDataState.ERROR
                    }
                })
        )
        return videoLiveData
    }

    fun getVideoTv(tvId: Int, token: String): MutableLiveData<VideoResponse> {
        composite.add(
            videoRepos.getTvVideo(tvId, token).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<VideoResponse>(){
                    override fun onSuccess(t: VideoResponse) {
                        videoLiveData.value = t
                        loadDataStatus.value = LoadDataState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        loadDataStatus.value = LoadDataState.ERROR
                    }
                })
        )

        return  videoLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    override fun onCleared() {
        composite.clear()
    }
}