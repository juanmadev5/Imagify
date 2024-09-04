package com.jmdev.app.imagify.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("profile_image") val profileImage: ProfileImage? = null
)

data class ProfileImage(
    @SerializedName("medium") val medium: String
)