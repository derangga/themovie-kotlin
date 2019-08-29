package com.themovie.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_upcoming")
class Upcoming (@PrimaryKey @ColumnInfo(name = "id") val id: Int?,
                @ColumnInfo(name = "tv_id") val mvId: Int,
                @ColumnInfo(name = "title") val title: String,
                @ColumnInfo(name = "date_release") val dateRelease: String,
                @ColumnInfo(name = "img_path") val imgPath: String?)