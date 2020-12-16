package com.themovie.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.entities.ui.*
import com.aldebaran.domain.repository.remote.TvRemoteSource
import com.aldebaran.event.Event
import com.aldebaran.network.Result

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailTvViewModel @ViewModelInject constructor(
    private val remote: TvRemoteSource
) : ViewModel() {

    private val _detailTvRes by lazy { MutableLiveData<DetailTv>() }
    private val _creditRes by lazy { MutableLiveData<List<Credit>>() }
    private val _recommendationTvRes by lazy { MutableLiveData<List<Tv>>() }
    private val _trailerRes by lazy { MutableLiveData<List<Video>>() }
    private val _reviewsRes by lazy { MutableLiveData<List<Review>>() }

    private val _loading by lazy { MutableLiveData(true) }
    val loading: LiveData<Boolean> get() = _loading
    private val _eventError by lazy { MutableLiveData<Event<Boolean>>() }
    val eventError: LiveData<Event<Boolean>> get() = _eventError

    val detailTvRes: LiveData<DetailTv> get() = _detailTvRes
    val creditMovieRes: LiveData<List<Credit>> get() = _creditRes
    val recommendationTvRes: LiveData<List<Tv>> get() = _recommendationTvRes
    val trailerTvRes: LiveData<List<Video>> get() = _trailerRes
    val reviewsTvRes: LiveData<List<Review>> get() = _reviewsRes

    fun getDetailTvRequest(filmId: Int){
        viewModelScope.launch {
            _loading.value = true
            val detailRes = async(IO) { remote.getDetailTv(filmId) }
            val creditsRes = async(IO) { remote.getCreditsTv(filmId) }
            val recomRes = async(IO) { remote.getRecommendationTv(filmId) }
            val trailer = async(IO) { remote.getTrailerTv(filmId) }
            val reviews = async(IO) { remote.getReviewTv(filmId, 1) }

            handleDetailTvResult(detailRes.await())
            handleCreditResult(creditsRes.await())
            handleRecommendedTvResult(recomRes.await())
            handleTrailerResult(trailer.await())
            handleReviewResult(reviews.await())

        }
    }

    private fun handleDetailTvResult(detailTv: Result<DetailTv>) {
        when (detailTv) {
            is Result.Success -> {
                _detailTvRes.value = detailTv.data
                _loading.value = false
            }
            is Result.Error -> {
                _loading.value = false
                _eventError.value = Event(true)
            }
        }
    }

    private fun handleCreditResult(credits: Result<List<Credit>>) {
        when (credits) {
            is Result.Success -> _creditRes.value = credits.data
            is Result.Error -> Unit
        }
    }

    private fun handleRecommendedTvResult(recommendedTv: Result<List<Tv>>) {
        when (recommendedTv) {
            is Result.Success -> _recommendationTvRes.value = recommendedTv.data
            is Result.Error -> Unit
        }
    }

    private fun handleTrailerResult(trailer: Result<List<Video>>) {
        when (trailer) {
            is Result.Success -> _trailerRes.value = trailer.data
            is Result.Error -> Unit
        }
    }

    private fun handleReviewResult(reviews: Result<List<Review>>) {
        when (reviews) {
            is Result.Success -> _reviewsRes.value = reviews.data
            is Result.Error -> Unit
        }
    }
}