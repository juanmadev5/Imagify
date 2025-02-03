package com.jmdev.app.imagify.model.unsplashphoto

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.photo.Urls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Photo(
    @SerializedName("id") val id: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("color") val color: String,
    @SerialName("blur_hash") val blurHash: String,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("likes") val likes: Int,
    @SerialName("liked_by_user") val likedByUser: Boolean,
    @SerialName("public_domain") val publicDomain: Boolean,
    @SerializedName("description") val description: String?,
    @SerializedName("exif") val exif: Exif?,
    @SerializedName("location") val location: Location?,
    @SerializedName("tags") val tags: List<Tag>,
    @SerialName("current_user_collections") val currentUserCollections: List<Collection>,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("links") val links: Links,
    @SerializedName("user") val user: User,
)