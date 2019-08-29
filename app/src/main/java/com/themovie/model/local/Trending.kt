package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_trending")
class Trending(@PrimaryKey @ColumnInfo(name = "id") val id: Int?,
               @ColumnInfo(name = "tv_id") val tvId: Int,
               @ColumnInfo(name = "title") val title: String,
               @ColumnInfo(name = "img_path") val imgPath: String?)