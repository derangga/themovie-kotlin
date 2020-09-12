package com.aldebaran.domain.interactor.movie

import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.remote.VideoResponse
import com.aldebaran.domain.interactor.UseCase
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GetTrailerMovieUseCase(
    private val movieRemoteSource: MovieRemoteSource
): UseCase<VideoResponse, Int>(){
    override suspend fun run(params: Int): Result<VideoResponse> {
        return movieRemoteSource.getTrailerMovie(params)
    }
}