package com.aldebaran.data.repository

import com.aldebaran.data.resultFlowData
import com.aldebaran.domain.entities.mapper.toGenre
import com.aldebaran.domain.entities.mapper.toGenreEntity
import com.aldebaran.domain.entities.ui.Genre
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.GenreLocalSource
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.network.Result
import kotlinx.coroutines.flow.Flow

class GenreRepository(
    private val localSource: GenreLocalSource,
    private val remoteSource: MovieRemoteSource
) : Repository.GenreRepos {
    override fun getGenreFromLocalOrRemote(): Flow<Result<List<Genre>>> {
        return resultFlowData(
            localSource = { localSource.getPartOfGenre().map { it.toGenre() } },
            remoteSource = { remoteSource.getGenreMovie() },
            saveData = { body ->
                if (localSource.isNotEmpty()) {
                    body.forEachIndexed { index, genre -> localSource.update(genre.toGenreEntity(index)) }
                } else {
                    val entity = body.map { it.toGenreEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getPartOfGenre().map { it.toGenre() }
            }
        )
    }
}