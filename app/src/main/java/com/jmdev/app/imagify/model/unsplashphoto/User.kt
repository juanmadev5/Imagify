package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class User(
    @SerializedName("id") val id: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerialName("portfolio_url") val portfolioUrl: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerialName("total_likes") val totalLikes: Int,
    @SerialName("total_photos") val totalPhotos: Int,
    @SerialName("total_collections") val totalCollections: Int,
    @SerializedName("links") val links: UserLinks
)