package com.aldebaran.domain.entities.mapper

import com.aldebaran.domain.entities.local.MovieEntity
import com.aldebaran.domain.entities.local.TrendingEntity
import com.aldebaran.domain.entities.local.UpcomingEntity
import com.aldebaran.domain.entities.remote.DetailMovieResponse
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.entities.ui.DetailMovie
import com.aldebaran.domain.entities.ui.Movie

fun MovieResponse.toMovie() = Movie(
    id = id ?: 0,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun DetailMovieResponse.toDetailMovie() = DetailMovie(
    id = id ?: 0,
    title = title.orEmpty(),
    rate = rate.orEmpty(),
    overview = overview.orEmpty(),
    status = status.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    popularity = popularity.orEmpty(),
    voteCount = voteCount.orEmpty(),
    genreList = genreList?.map { it.toGenre() }.orEmpty()
)

fun DetailMovie?.orEmpty() = this ?: DetailMovie(
    0, "", "", "", "", "",
    "", "", "", "", emptyList()
)

fun MovieResponse.empty() = Movie(
    0, "", "",
    "", "", "", ""
)

fun MovieEntity.toMovie() = Movie(
    id = id ?: 0,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun Movie.toMovieEntity(pkId: Int? = null): MovieEntity {
    return MovieEntity(
        pkId = pkId,
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun Movie.toTrendingEntity(pkId: Int? = null): TrendingEntity {
    return TrendingEntity(
        pkId = pkId,
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun Movie.toUpcomingEntity(pkId: Int? = null): UpcomingEntity {
    return UpcomingEntity(
        pkId = pkId,
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview,
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun TrendingEntity.toMovie() = Movie(
    id = id ?: 0,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun UpcomingEntity.toMovie() = Movie(
    id = id ?: 0,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun MovieEntity?.orEmpty() = this ?: Movie(
    0, "", "",
    "", "", "", ""
)