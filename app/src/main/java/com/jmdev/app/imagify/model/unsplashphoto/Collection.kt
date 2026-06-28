package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class Collection(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("published_at") val publishedAt: String? = null,
    @SerializedName("last_collected_at") val lastCollectedAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
)