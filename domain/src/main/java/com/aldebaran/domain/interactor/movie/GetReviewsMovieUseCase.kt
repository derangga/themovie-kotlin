package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.entities.remote.ReviewsResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetReviewsMovieUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<DataList<ReviewsResponse>, Int>(){
    override suspend fun run(params: Int): Result<DataList<ReviewsResponse>> {
        return movieRemoteSource.getReviewMovie(params)
    }
}