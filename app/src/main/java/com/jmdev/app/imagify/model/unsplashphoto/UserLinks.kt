package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class UserLinks(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("photos") val photos: String,
    @SerializedName("likes") val likes: String,
    @SerializedName("portfolio") val portfolio: String?,
)
