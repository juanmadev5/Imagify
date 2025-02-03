package com.jmdev.app.imagify.model

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.photo.FeedPhoto
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class SearchPhotosResult(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<FeedPhoto>,
)