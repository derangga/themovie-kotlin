package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.db.Genre
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailMvViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailLiveMovieData: MutableLiveData<FetchDetailMovieData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    // Data Movie
    private val title: MutableLiveData<String> = MutableLiveData()
    private val rating: MutableLiveData<String> = MutableLiveData()
    private val totalVote: MutableLiveData<String> = MutableLiveData()
    private val popularity: MutableLiveData<String> = MutableLiveData()
    private val releaseDate: MutableLiveData<String> = MutableLiveData()
    private val description: MutableLiveData<String> = MutableLiveData()
    private val status: MutableLiveData<String> = MutableLiveData()
    private val genre: MutableLiveData<String> = MutableLiveData()
    private var imageUrl: MutableLiveData<String> = MutableLiveData()
    private var backdropImage: MutableLiveData<String> = MutableLiveData()

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

    fun setDetailMovieData(detailMovie: DetailMovieResponse){
        title.value = detailMovie.title
        rating.value = detailMovie.rate
        totalVote.value = "(${detailMovie.voteCount} Reviews)"
        popularity.value = detailMovie.popularity
        releaseDate.value = "Date Release : ${detailMovie.releaseDate.convertDate()}"
        description.value = detailMovie.overview
        status.value = detailMovie.status
        genre.value = "Genre : ${concateGenres(detailMovie.genreList)}"
        imageUrl.value = ApiUrl.IMG_POSTER + detailMovie.posterPath.toString()
        backdropImage.value = ApiUrl.IMG_BACK + detailMovie.backdropPath.toString()
    }

    fun getTitle(): LiveData<String> = title
    fun getRating(): LiveData<String> = rating
    fun getTotalVote(): LiveData<String> = totalVote
    fun getPopularity(): LiveData<String> = popularity
    fun getDescription(): LiveData<String> = description
    fun getReleaseDate(): LiveData<String> = releaseDate
    fun getStatus(): LiveData<String> = status
    fun getBackdropImage(): LiveData<String> = backdropImage
    fun getGenre(): LiveData<String> = genre
    fun getImageUrl(): LiveData<String> = imageUrl


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