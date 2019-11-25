package com.themovie.model.online.detail

import com.google.gson.annotations.SerializedName
import com.themovie.helper.concatListGenres
import com.themovie.helper.convertDate
import com.themovie.model.db.Genre
import com.themovie.restapi.ApiUrl

data class DetailMovieResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val title: String,
    @SerializedName("vote_average") val rate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("status") val status: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("vote_count") val voteCount: String,
    @SerializedName("genres") val genreList: List<Genre>
){
    fun getConcateListGenre(): String = "Genre: ${genreList.concatListGenres()}"
    fun getFormatReleaseDate(): String = "Date Release: ${releaseDate.convertDate()}"
    fun getReviews(): String = "(${voteCount} Reviews)"
    fun getPosterImageUrl(): String = "${ApiUrl.IMG_POSTER}${posterPath.toString()}"
    fun getBackdropImageUrl(): String = "${ApiUrl.IMG_BACK}${backdropPath.toString()}"
}