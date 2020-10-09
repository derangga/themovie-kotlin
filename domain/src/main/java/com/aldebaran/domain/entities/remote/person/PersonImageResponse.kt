package com.aldebaran.domain.entities.remote.person

import com.google.gson.annotations.SerializedName

data class PersonImageResponse(
    @SerializedName("profiles") val imageList: List<PersonImage>
)

data class PersonImage (
    @SerializedName("width") val width: Int? = 0,
    @SerializedName("height") val height: Int? = 0,
    @SerializedName("file_path") val filePath: String? = ""
)