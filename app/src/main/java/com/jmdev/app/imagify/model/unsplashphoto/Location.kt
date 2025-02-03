package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Location(
    @SerializedName("city") val city: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("position") val position: Position?
)
