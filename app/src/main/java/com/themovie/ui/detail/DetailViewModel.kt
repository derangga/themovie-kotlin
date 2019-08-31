package com.themovie.ui.detail

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
import com.themovie.model.online.FetchDetailData
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.Genre
import com.themovie.repos.fromapi.DetailRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailRepos: DetailRepos = DetailRepos()
    private val detailLiveData: MutableLiveData<FetchDetailData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    // Data Movie
    private val title: MutableLiveData<String> = MediatorLiveData()
    private val rating: MutableLiveData<String> = MediatorLiveData()
    private val totalVote: MutableLiveData<String> = MediatorLiveData()
    private val popularity: MutableLiveData<String> = MediatorLiveData()
    private val releaseDate: MutableLiveData<String> = MediatorLiveData()
    private val description: MutableLiveData<String> = MediatorLiveData()
    private val status: MutableLiveData<String> = MediatorLiveData()
    private val genre: MutableLiveData<String> = MediatorLiveData()
    private var imageUrl: MutableLiveData<String> = MediatorLiveData()

    fun getDetailMovieRequest(movieId: Int, token: String): MutableLiveData<FetchDetailData> {
        composite.add(
            detailRepos.getDetailData(token, movieId).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<FetchDetailData>(){
                    override fun onComplete() {

                    }

                    override fun onNext(t: FetchDetailData) {
                        detailLiveData.value = t
                        loadDataStatus.value = LoadDataState.LOADED
                    }

                    override fun onError(e: Throwable) {
                        Log.e("tag", e.message.toString())
                        loadDataStatus.value = LoadDataState.ERROR
                    }
                })
        )
        return detailLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    fun setDetailMovieData(detailMovie: DetailMovieResponse){
        title.value = detailMovie.title
        rating.value = detailMovie.rate
        totalVote.value = detailMovie.voteCount
        popularity.value = detailMovie.popularity
        releaseDate.value = DateConverter.convert(detailMovie.releaseDate)
        description.value = detailMovie.overview
        status.value = detailMovie.status
        genre.value = concateGenres(detailMovie.genreList)
        imageUrl.value = ApiUrl.IMG_POSTER + detailMovie.posterPath.toString()
    }

    fun getTitle(): LiveData<String> {
        return title
    }

    fun getRating(): LiveData<String> {
        return rating
    }

    fun getTotalVote(): LiveData<String> {
        return totalVote
    }

    fun getPopularity(): LiveData<String> {
        return popularity
    }

    fun getDescription(): LiveData<String> {
        return description
    }

    fun getReleaseDate(): LiveData<String> {
        return releaseDate
    }

    fun getStatus(): LiveData<String> {
        return status
    }

    fun getGenre(): LiveData<String> {
        return genre
    }

    fun getImageUrl(): LiveData<String> {
        return imageUrl
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?){
            ImageCache.setImageViewUrl(view.context, imageUrl.toString(), view)
        }
    }

    private fun concateGenres(genreList: List<Genre>): String {
        val genre = StringBuilder()
        for(i in genreList.indices){
            if(genreList.size > 1){
                if(i != genreList.size - 1) {
                    genre.append(genreList[i].name).append(", ")
                } else genre.append(genreList[i].name)
            } else genre.append(genreList[i].name)
        }
        return genre.toString()
    }

    override fun onCleared() {
        composite.clear()
    }
}