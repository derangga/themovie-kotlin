package com.aldebaran.domain.entities

import com.google.gson.annotations.SerializedName

data class ResponseParser<T>(
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") val results: List<T>?
)