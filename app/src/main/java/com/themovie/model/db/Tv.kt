package com.themovie.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_tv")
data class Tv (
    @PrimaryKey(autoGenerate = true)
    val pkId: Int = 0,
    @ColumnInfo(name = "tvId")
    @SerializedName("id") val id: Int,
    @ColumnInfo(name = "title")
    @SerializedName("name") val name: String,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average") val voteAverage: String,
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count") val voteCount: String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "overview")
    @SerializedName("overview") val overview: String
)