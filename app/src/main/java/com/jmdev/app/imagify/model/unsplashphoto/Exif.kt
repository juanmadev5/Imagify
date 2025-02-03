package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Exif(
    @SerializedName("make") val make: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("name") val name: String?,
    @SerialName("exposure_time") val exposureTime: String?,
    @SerializedName("aperture") val aperture: String?,
    @SerialName("focal_length") val focalLength: String?,
    @SerializedName("iso") val iso: Int?,
)