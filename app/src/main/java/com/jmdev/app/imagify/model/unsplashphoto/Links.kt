package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Links(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("download") val download: String,
    @SerialName("download_location") val downloadLocation: String
)