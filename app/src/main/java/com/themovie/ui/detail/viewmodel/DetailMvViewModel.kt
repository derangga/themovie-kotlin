package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.*
import com.themovie.helper.DateConverter
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailMovieData
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.Genre
import com.themovie.repos.fromapi.DetailMovieRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailMvViewModel(private val detailMovieRepos: DetailMovieRepos) : ViewModel() {

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

    init {
        viewModelScope.launch {
            try {
                val response = detailMovieRepos.getDetailData(ApiUrl.TOKEN, filmId)
                if(response != null){
                    detailLiveMovieData.value = response
                    loadDataStatus.value = LoadDataState.LOADED
                } else loadDataStatus.value = LoadDataState.ERROR

            } catch (e: Exception){
                loadDataStatus.value = LoadDataState.ERROR
            }
        }
    }

    fun getDetailMovieRequest(): MutableLiveData<FetchDetailMovieData> {

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
        releaseDate.value = "Date Release : ${DateConverter.convert(detailMovie.releaseDate)}"
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