package com.jmdev.app.imagify.model.photo

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class FeedPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("blur_hash") val blurHash: String? = null,
    @SerializedName("likes") val likes: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("user") val user: User? = null,
)