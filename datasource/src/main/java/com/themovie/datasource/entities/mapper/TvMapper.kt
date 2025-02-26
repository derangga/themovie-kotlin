package com.themovie.datasource.entities.mapper

import com.themovie.datasource.entities.local.TvEntity
import com.themovie.datasource.entities.remote.DetailTvResponse
import com.themovie.datasource.entities.remote.SeasonTvResponse
import com.themovie.datasource.entities.remote.TvResponse
import com.themovie.datasource.entities.ui.DetailTv
import com.themovie.datasource.entities.ui.SeasonTv
import com.themovie.datasource.entities.ui.Tv

fun TvResponse.toTv() = Tv(
    id = id ?: 0,
    name = name.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    voteCount = voteCount.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty()
)

fun TvResponse?.empty() = Tv(
    0, "","","",
    "","",""
)

fun TvEntity.toTv() = Tv(
    id = id ?: 0,
    name = name.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    voteCount = voteCount.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    overview = overview.orEmpty()
)

fun Tv.toTvEntity(pkId: Int? = null): TvEntity {
    return TvEntity(
        pkId = pkId,
        id = id,
        name = name,
        voteAverage = voteAverage,
        voteCount = voteCount,
        posterPath = posterPath,
        backdropPath = backdropPath,
        overview = overview
    )
}

fun DetailTvResponse.toDetailTv() = DetailTv(
    id = id ?: 0,
    name = name.orEmpty(),
    voteAverage = voteAverage.orEmpty(),
    voteCount = voteCount ?: 0,
    status = status.orEmpty(),
    popularity = popularity.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    firstAirDate = firstAirDate.orEmpty(),
    overview = overview.orEmpty(),
    numberOfEpisodes = numberOfEpisodes ?: 0,
    numberOfSeasons = numberOfSeasons ?: 0,
    genreList = genreList?.map { it.toGenre() }.orEmpty(),
    seasonResponses = season?.map { it.toSeasonTv() }.orEmpty()
)

fun DetailTv?.orEmpty() = this ?: DetailTv(
    0,"","",0,"","","","",
    "","",0,0, emptyList(), emptyList()
)

fun SeasonTvResponse.toSeasonTv() = SeasonTv(
    id = id ?: 0,
    name = name.orEmpty(),
    totalEpisode = totalEpisode ?: 0,
    posterPath = posterPath.orEmpty()
)