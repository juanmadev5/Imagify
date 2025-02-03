package com.jmdev.app.imagify.model.photo

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class ProfileImage(
    @SerializedName("medium") val medium: String,
)