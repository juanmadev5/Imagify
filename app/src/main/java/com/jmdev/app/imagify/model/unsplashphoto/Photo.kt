package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.photo.Urls

@Stable
data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("color") val color: String,
    @SerializedName("blur_hash") val blurHash: String? = null,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("liked_by_user") val likedByUser: Boolean = false,
    @SerializedName("public_domain") val publicDomain: Boolean = false,
    @SerializedName("description") val description: String?,
    @SerializedName("exif") val exif: Exif?,
    @SerializedName("location") val location: Location?,
    @SerializedName("tags") val tags: List<Tag> = emptyList(),
    @SerializedName("current_user_collections") val currentUserCollections: List<Collection> = emptyList(),
    @SerializedName("urls") val urls: Urls,
    @SerializedName("links") val links: Links,
    @SerializedName("user") val user: User,
)