package com.themovie.repos

import com.themovie.model.db.*
import com.themovie.model.online.PopularResponse
import com.themovie.model.online.detail.*
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.model.online.genre.GenreResponse
import com.themovie.model.online.person.*
import com.themovie.model.online.upcoming.UpcomingResponse
import com.themovie.model.online.video.VideoResponse
import com.themovie.model.online.video.Videos
import com.themovie.restapi.Result

class FakeRemoteSource: RemoteSource {
    override suspend fun getPopularMovie(page: Int): Result<PopularResponse> {
        val response = listOf(
            Trending(1, 419704, "Ad Astra"),
            Trending(2, 475430, "Artemis Fowl"),
            Trending(3, 454626, "Sonic the Hedgehog")
        )
        return Result.success(PopularResponse(response, 1))
    }

    override suspend fun getGenreMovie(): Result<GenreResponse> {
        val response = listOf(
            Genre(1,18, "Action"),
            Genre(2,12, "Adventure"),
            Genre(3,18, "Animation")
        )
        return Result.success(GenreResponse(response))
    }

    override suspend fun getUpcomingMovie(page: Int): Result<UpcomingResponse> {
        val response = listOf(
            Upcoming(1, 419704, "Ad Astra"),
            Upcoming(2, 475430, "Artemis Fowl"),
            Upcoming(3, 454626, "Sonic the Hedgehog")
        )
        return Result.success(UpcomingResponse(response))
    }

    override suspend fun getDiscoverTv(page: Int): Result<TvResponse> {
        val response = listOf(
            Tv(1, 70523, "Dark"),
            Tv(2, 2734, "Law & Order: Special Victims Unit"),
            Tv(3, 60572, "Pokémon")
        )
        return Result.success(TvResponse(response))
    }

    override suspend fun getDiscoverMovie(page: Int): Result<MoviesResponse> {
        val response = listOf(
            Movies(1, 419704, "Ad Astra"),
            Movies(2, 475430, "Artemis Fowl"),
            Movies(3, 454626, "Sonic the Hedgehog")
        )
        return Result.success(MoviesResponse(response))
    }

    override suspend fun getDetailMovie(movieId: Int): Result<DetailMovieResponse> {
        val detail = DetailMovieResponse(419704, "Ad Astra", genreList = emptyList())
        return Result.success(detail)
    }

    override suspend fun getDetailTv(tvId: Int): Result<DetailTvResponse> {
        val detail = DetailTvResponse(60572, "Pokémon", genreList = emptyList(), seasons = emptyList())
        return Result.success(detail)
    }

    override suspend fun getCreditMovie(movieId: Int): Result<CastResponse> {
        val credits = listOf(
            Credits(287,"Brad Pitt","Roy McBride"),
            Credits(272,"Tommy Lee Jones","H. Clifford McBride"),
            Credits(273,"Ruth Negga","Helen Lantos")
        )
        return Result.success(CastResponse(credits))
    }

    override suspend fun getCreditTv(tvId: Int): Result<CastResponse> {
        val credits = listOf(
            Credits(287,"Brad Pitt","Roy McBride"),
            Credits(272,"Tommy Lee Jones","H. Clifford McBride"),
            Credits(273,"Ruth Negga","Helen Lantos")
        )
        return Result.success(CastResponse(credits))
    }

    override suspend fun getRecommendationMovie(movieId: Int): Result<MoviesResponse> {
        val response = listOf(
            Movies(1, 419704, "Ad Astra"),
            Movies(2, 475430, "Artemis Fowl"),
            Movies(3, 454626, "Sonic the Hedgehog")
        )
        return Result.success(MoviesResponse(response))
    }

    override suspend fun getRecommendationTv(tvId: Int): Result<TvResponse> {
        val response = listOf(
            Tv(1, 70523, "Dark"),
            Tv(2, 2734, "Law & Order: Special Victims Unit"),
            Tv(3, 60572, "Pokémon")
        )
        return Result.success(TvResponse(response))
    }

    override suspend fun getReviewMovie(movieId: Int): Result<ReviewResponse> {
        val reviews = listOf(
            Reviews("1", "a"),
            Reviews("2", "b"),
            Reviews("3", "c")
        )
        return Result.success(ReviewResponse(1,1, reviews))
    }

    override suspend fun getReviewTv(tvId: Int): Result<ReviewResponse> {
        val reviews = listOf(
            Reviews("1", "a"),
            Reviews("2", "b"),
            Reviews("3", "c")
        )
        return Result.success(ReviewResponse(1,1, reviews))
    }

    override suspend fun getTrailerMovie(movieId: Int): Result<VideoResponse> {
        val videos = listOf(
            Videos("1", "5e93b2afd55e4d001a19fa27", "A"),
            Videos("2", "5e93b248875d1a001944ef1d", "B"),
            Videos("3", "5e93b30c54508d001a177e3a", "C")
        )
        return Result.success(VideoResponse(videos))
    }

    override suspend fun getTrailerTv(tvId: Int): Result<VideoResponse> {
        val videos = listOf(
            Videos("1", "5e93b2afd55e4d001a19fa27", "A"),
            Videos("2", "5e93b248875d1a001944ef1d", "B"),
            Videos("3", "5e93b30c54508d001a177e3a", "C")
        )
        return Result.success(VideoResponse(videos))
    }

    override suspend fun getDetailPerson(personId: Int): Result<PersonResponse> {
        val peron = PersonResponse(1, "John Wick")
        return Result.success(peron)
    }

    override suspend fun getPersonFilm(personId: Int): Result<PersonFilmResponse> {
        val film = listOf(
            Filmography(1, "Ad Astra"),
            Filmography(2, "Artemis Fowl"),
            Filmography(3, "Sonic the Hedgehog")
        )
        return Result.success(PersonFilmResponse(film))
    }

    override suspend fun getPersonPict(personId: Int): Result<PersonImageResponse> {
        val photo = listOf(
            PersonImage(1,1, "A"),
            PersonImage(2,2, "B")
        )
        return Result.success(PersonImageResponse(photo))
    }

    override suspend fun getSuggestSearchTv(query: String): Result<TvResponse> {
        val response = listOf(
            Tv(1, 70523, "Dark"),
            Tv(2, 2734, "Law & Order: Special Victims Unit"),
            Tv(3, 60572, "Pokémon")
        )
        return Result.success(TvResponse(response))
    }

    override suspend fun getSuggestSearchMovie(query: String): Result<MoviesResponse> {
        val response = listOf(
            Movies(1, 419704, "Ad Astra"),
            Movies(2, 475430, "Artemis Fowl"),
            Movies(3, 454626, "Sonic the Hedgehog")
        )
        return Result.success(MoviesResponse(response))
    }

}