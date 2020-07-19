package com.themovie.model.online.person

import com.google.gson.annotations.SerializedName
import com.themovie.helper.convertDate
import com.themovie.restapi.ApiUrl

data class PersonResponse(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("birthday") val birthday: String? = "",
    @SerializedName("place_of_birth") val placeOfBirth: String? = "",
    @SerializedName("profile_path") val profilePath: String? = "",
    @SerializedName("biography") val biography: String? = "",
    @SerializedName("gender") val gender: String? = ""
){
    fun getBirthDate(): String{
        return if(!birthday.isNullOrEmpty()) "Born: ${birthday.convertDate()}, ${placeOfBirth.orEmpty()}"
        else ""
    }
    
    fun getProfilePict(): String{
        return "${ApiUrl.IMG_POSTER}${profilePath.orEmpty()}"
    }
}