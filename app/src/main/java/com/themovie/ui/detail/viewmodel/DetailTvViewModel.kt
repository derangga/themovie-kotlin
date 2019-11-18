package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.LoadDataState
import com.themovie.helper.convertDate
import com.themovie.model.db.Genre
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.repos.fromapi.ApiRepository
import com.themovie.restapi.ApiCallback
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import kotlin.Exception

class DetailTvViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailTvLiveData: MutableLiveData<FetchDetailTvData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    // Data Detail Tv
    private val title: MutableLiveData<String> = MutableLiveData()
    private val rating: MutableLiveData<String> = MutableLiveData()
    private val totalVote: MutableLiveData<String> = MutableLiveData()
    private val popularity: MutableLiveData<String> = MutableLiveData()
    private val releaseData: MutableLiveData<String> = MutableLiveData()
    private val description: MutableLiveData<String> = MutableLiveData()
    private val status: MutableLiveData<String> = MutableLiveData()
    private val genre: MutableLiveData<String> = MutableLiveData()
    private val totalEps: MutableLiveData<String> = MutableLiveData()
    private val totalSeason: MutableLiveData<String> = MutableLiveData()
    private val imageUrl: MutableLiveData<String> = MutableLiveData()
    private val backdropImage: MutableLiveData<String> = MutableLiveData()

    companion object {
        private var filmId = 0
        fun setFilmId(filmId: Int){
            this.filmId = filmId
        }
    }

    fun getDetailTvRequest(): MutableLiveData<FetchDetailTvData> {
        viewModelScope.launch {
            apiRepository.getDetailDataTv(filmId, object: ApiCallback<FetchDetailTvData>{
                override fun onSuccessRequest(response: FetchDetailTvData?) {
                    loadDataStatus.value = LoadDataState.LOADED
                    detailTvLiveData.value = response
                }

                override fun onErrorRequest(errorBody: ResponseBody?) {
                    loadDataStatus.value = LoadDataState.ERROR
                }

                override fun onFailure(e: Exception) {
                    loadDataStatus.value = LoadDataState.ERROR
                }
            })
        }
        return detailTvLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    fun setDetailTvData(detailTv: DetailTvResponse){
        title.value = detailTv.name
        rating.value = detailTv.voteAverage
        totalVote.value = "(${detailTv.voteCount} Reviews)"
        popularity.value = detailTv.popularity
        releaseData.value = "Release Date : ${detailTv.firstAirDate.convertDate()}"
        description.value = detailTv.overview
        status.value = detailTv.status
        genre.value = "Genre : ${concateGenres(detailTv.genreList)}"
        totalEps.value = detailTv.numberOfEpisodes.toString()
        totalSeason.value = detailTv.numberOfSeasons.toString()
        imageUrl.value = ApiUrl.IMG_POSTER + detailTv.posterPath.toString()
        backdropImage.value = ApiUrl.IMG_BACK + detailTv.backdropPath.toString()
    }

    fun getTitle(): LiveData<String> = title
    fun getRating(): LiveData<String> = rating
    fun getTotalVote(): LiveData<String> = totalVote
    fun getPopularity(): LiveData<String> = popularity
    fun getDescription(): LiveData<String> = description
    fun getReleaseDate(): LiveData<String> = releaseData
    fun getStatus(): LiveData<String> = status
    fun getGenre(): LiveData<String> = genre
    fun getTotalEps(): LiveData<String> = totalEps
    fun getTotalSeason(): LiveData<String> = totalSeason
    fun getImageUrl(): LiveData<String> = imageUrl
    fun getBackdropImage(): LiveData<String> = backdropImage

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