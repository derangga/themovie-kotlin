package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.Genre
import com.aldebaran.domain.entities.local.GenreEntity
import com.aldebaran.domain.entities.toGenreEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.GenreLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource

class GenreRepository (
    private val local: GenreLocalSource,
    private val remote: MovieRemoteSource
): Repository.GenreRepos {
    override fun getGenreFromLocalOrRemote() : LiveData<Result<List<GenreEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getPartOfGenre() },
            networkCall = { remote.getGenreMovie() },
            saveCallResult = { res ->
                val rows = local.genreRows()
                if(rows == 0) {
                    res.genres.forEach { local.insertGenre(it.toGenreEntity()) }
                }
                else {
                    res.genres.forEachIndexed { key, genre ->
                        local.updateGenre(genre.toGenreEntity(key + 1))
                    }
                }
            })
    }
}