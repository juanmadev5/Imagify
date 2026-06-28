package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class Exif(
    @SerializedName("make") val make: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("exposure_time") val exposureTime: String?,
    @SerializedName("aperture") val aperture: String?,
    @SerializedName("focal_length") val focalLength: String?,
    @SerializedName("iso") val iso: Int?,
)
