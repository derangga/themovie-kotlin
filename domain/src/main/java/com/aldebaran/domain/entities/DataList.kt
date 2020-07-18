package com.aldebaran.domain.entities

import com.google.gson.annotations.SerializedName

data class DataList<T>(
    @SerializedName("total_pages") val totalPages: Int? = 0,
    @SerializedName("results") val results: List<T> = emptyList()
)