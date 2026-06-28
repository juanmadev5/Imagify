package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class User(
    @SerializedName("id") val id: String,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("portfolio_url") val portfolioUrl: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("total_likes") val totalLikes: Int = 0,
    @SerializedName("total_photos") val totalPhotos: Int = 0,
    @SerializedName("total_collections") val totalCollections: Int = 0,
    @SerializedName("links") val links: UserLinks,
)
