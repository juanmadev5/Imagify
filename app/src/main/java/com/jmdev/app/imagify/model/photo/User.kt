package com.jmdev.app.imagify.model.photo

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("profile_image") val profileImage: ProfileImage? = null,
)