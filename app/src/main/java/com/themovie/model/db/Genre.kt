package com.themovie.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_genre")
data class Genre(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int? = 0,
    @ColumnInfo(name = "genreId")
    @SerializedName("id") val id: Int,
    @ColumnInfo(name = "name")
    @SerializedName("name") val name: String
)