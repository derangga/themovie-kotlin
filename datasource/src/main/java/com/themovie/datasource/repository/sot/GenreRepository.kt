package com.themovie.datasource.repository.sot

import com.themovie.core.network.Result
import com.themovie.datasource.entities.mapper.toGenre
import com.themovie.datasource.entities.mapper.toGenreEntity
import com.themovie.datasource.entities.ui.Genre
import com.themovie.datasource.repository.local.GenreLocalSource
import com.themovie.datasource.repository.remote.MovieRemoteSource
import com.themovie.datasource.repository.Repository
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
                    body.forEachIndexed { index, genre -> localSource.update(genre.toGenreEntity(index + 1)) }
                } else {
                    val entity = body.map { it.toGenreEntity() }
                    localSource.insertAll(entity)
                }
                localSource.getPartOfGenre().map { it.toGenre() }
            }
        )
    }
}