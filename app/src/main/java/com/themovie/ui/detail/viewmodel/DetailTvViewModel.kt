package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.*
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailTvResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.video.VideoResponse
import com.themovie.repos.RemoteSource
import com.themovie.restapi.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailTvViewModel @Inject constructor(
    private val remote: RemoteSource
) : ViewModel() {

    private val _detailTvRes by lazy { MutableLiveData<Result<DetailTvResponse>>() }
    private val _creditRes by lazy { MutableLiveData<Result<CastResponse>>() }
    private val _recommendationTvRes by lazy { MutableLiveData<Result<TvResponse>>() }
    private val _trailerRes by lazy { MutableLiveData<Result<VideoResponse>>() }
    private val _reviewsRes by lazy { MutableLiveData<Result<ReviewResponse>>() }

    val detailTvRes: LiveData<Result<DetailTvResponse>> get() = _detailTvRes
    val creditMovieRes: LiveData<Result<CastResponse>> get() = _creditRes
    val recommendationTvRes: LiveData<Result<TvResponse>> get() = _recommendationTvRes
    val trailerTvRes: LiveData<Result<VideoResponse>> get() = _trailerRes
    val reviewsTvRes: LiveData<Result<ReviewResponse>> get() = _reviewsRes

    fun getDetailTvRequest(filmId: Int){
        viewModelScope.launch {
            val detailRes = async(IO) { remote.getDetailTv(filmId) }
            val creditsRes = async(IO) { remote.getCreditTv(filmId) }
            val recomRes = async(IO) { remote.getRecommendationTv(filmId) }
            val trailer = async(IO) { remote.getTrailerTv(filmId) }
            val reviews = async(IO) { remote.getReviewTv(filmId) }

            _detailTvRes.value = detailRes.await()
            _creditRes.value = creditsRes.await()
            _recommendationTvRes.value = recomRes.await()
            _trailerRes.value = trailer.await()
            _reviewsRes.value = reviews.await()

        }
    }

}