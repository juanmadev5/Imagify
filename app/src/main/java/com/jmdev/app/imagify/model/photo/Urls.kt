package com.jmdev.app.imagify.model.photo

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Urls(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
)