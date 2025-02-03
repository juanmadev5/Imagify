package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Position(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?
)