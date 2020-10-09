package com.aldebaran.domain.entities

import com.aldebaran.domain.entities.local.*
import com.aldebaran.domain.entities.remote.GenreRemote
import com.aldebaran.domain.entities.remote.MovieResponse
import com.aldebaran.domain.entities.remote.TvResponse

fun MovieResponse.toMovieEntity(pkId: Int = 0): MovieEntity {
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

fun MovieResponse.toTrendingEntity(pkId: Int = 0): TrendingEntity {
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

fun MovieResponse.toUpcomingEntity(pkId: Int = 0): UpcomingEntity {
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

fun GenreRemote.toGenreEntity(pkId: Int = 0): GenreEntity {
    return GenreEntity(
        pkId = pkId,
        id = id,
        name = name
    )
}

fun TvResponse.toTvEntity(pkId: Int = 0): TvEntity {
    return TvEntity(
        pkId =  pkId,
        id = id,
        name = name,
        voteAverage = voteAverage,
        voteCount = voteCount,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview
    )
}