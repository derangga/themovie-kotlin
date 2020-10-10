package com.themovie.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.*
import com.aldebaran.domain.repository.remote.TvRemoteSource

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailTvViewModel @ViewModelInject constructor(
    private val remote: TvRemoteSource
) : ViewModel() {

    private val _detailTvRes by lazy { MutableLiveData<Result<DetailTvResponse>>() }
    private val _creditRes by lazy { MutableLiveData<Result<CastResponse>>() }
    private val _recommendationTvRes by lazy { MutableLiveData<Result<DataList<TvResponse>>>() }
    private val _trailerRes by lazy { MutableLiveData<Result<VideoResponse>>() }
    private val _reviewsRes by lazy { MutableLiveData<Result<DataList<ReviewsResponse>>>() }

    val detailTvRes: LiveData<Result<DetailTvResponse>> get() = _detailTvRes
    val creditMovieRes: LiveData<Result<CastResponse>> get() = _creditRes
    val recommendationTvRes: LiveData<Result<DataList<TvResponse>>> get() = _recommendationTvRes
    val trailerTvRes: LiveData<Result<VideoResponse>> get() = _trailerRes
    val reviewsTvRes: LiveData<Result<DataList<ReviewsResponse>>> get() = _reviewsRes

    fun getDetailTvRequest(filmId: Int){
        viewModelScope.launch {
            val detailRes = async(IO) { remote.getDetailTv(filmId) }
            val creditsRes = async(IO) { remote.getCreditsTv(filmId) }
            val recomRes = async(IO) { remote.getRecommendationTv(filmId) }
            val trailer = async(IO) { remote.getTrailerTv(filmId) }
            val reviews = async(IO) { remote.getReviewTv(filmId, 1) }

            _detailTvRes.value = detailRes.await()
            _creditRes.value = creditsRes.await()
            _recommendationTvRes.value = recomRes.await()
            _trailerRes.value = trailer.await()
            _reviewsRes.value = reviews.await()

        }
    }

}