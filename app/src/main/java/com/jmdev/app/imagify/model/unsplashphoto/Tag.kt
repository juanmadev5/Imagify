package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Tag(
    @SerializedName("title") val title: String
)