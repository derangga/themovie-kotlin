package com.themovie.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.themovie.helper.DateConverter
import com.themovie.helper.LoadDataState
import com.themovie.model.online.FetchDetailTvData
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.Genre
import com.themovie.repos.fromapi.DetailTvRepos
import com.themovie.restapi.ApiUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailTvViewModel(private val detailTvRepos: DetailTvRepos) : ViewModel() {

    private val composite: CompositeDisposable = CompositeDisposable()
    private val detailTvLiveData: MutableLiveData<FetchDetailTvData> = MutableLiveData()
    private val loadDataStatus: MutableLiveData<LoadDataState> = MutableLiveData()

    // Data Detail Tv
    private val title: MutableLiveData<String> = MediatorLiveData()
    private val rating: MutableLiveData<String> = MediatorLiveData()
    private val totalVote: MutableLiveData<String> = MediatorLiveData()
    private val popularity: MutableLiveData<String> = MediatorLiveData()
    private val releaseData: MutableLiveData<String> = MediatorLiveData()
    private val description: MutableLiveData<String> = MediatorLiveData()
    private val status: MutableLiveData<String> = MediatorLiveData()
    private val genre: MutableLiveData<String> = MediatorLiveData()
    private val totalEps: MutableLiveData<String> = MediatorLiveData()
    private val totalSeason: MutableLiveData<String> = MediatorLiveData()
    private val imageUrl: MutableLiveData<String> = MediatorLiveData()

    fun getDetailTvRequest(filmId: Int): MutableLiveData<FetchDetailTvData> {
        viewModelScope.launch {
            try {
                val response = detailTvRepos.getDetailData(ApiUrl.TOKEN, filmId)
                if(response != null){
                    detailTvLiveData.value = response
                    loadDataStatus.value = LoadDataState.LOADED
                } else loadDataStatus.value = LoadDataState.ERROR

            }catch (e: Exception){
                loadDataStatus.value = LoadDataState.ERROR
            }
        }
        return detailTvLiveData
    }

    fun getLoadDataStatus(): MutableLiveData<LoadDataState> {
        return loadDataStatus
    }

    fun setDetailTvData(detailTv: DetailTvResponse){
        title.value = detailTv.name
        rating.value = detailTv.voteAverage
        totalVote.value = detailTv.voteCount.toString()
        popularity.value = detailTv.popularity
        releaseData.value = DateConverter.convert(detailTv.firstAirDate)
        description.value = detailTv.overview
        status.value = detailTv.status
        genre.value = concateGenres(detailTv.genreList)
        totalEps.value = detailTv.numberOfEpisodes.toString()
        totalSeason.value = detailTv.numberOfSeasons.toString()
        imageUrl.value = ApiUrl.IMG_POSTER + detailTv.posterPath.toString()
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
        return releaseData
    }

    fun getStatus(): LiveData<String> {
        return status
    }

    fun getGenre(): LiveData<String> {
        return genre
    }

    fun getTotalEps(): LiveData<String> {
        return totalEps
    }

    fun getTotalSeason(): LiveData<String> {
        return totalSeason
    }

    fun getImageUrl(): LiveData<String> {
        return imageUrl
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