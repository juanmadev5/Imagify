package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Collection(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerialName("published_at") val publishedAt: String,
    @SerialName("last_collected_at") val lastCollectedAt: String,
    @SerialName("updated_at") val updatedAt: String,
)