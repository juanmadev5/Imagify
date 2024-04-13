package com.jmdev.app.imagify.model.collection

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.photo.Links
import com.jmdev.app.imagify.model.photo.Photo
import com.jmdev.app.imagify.model.photo.Tag
import com.jmdev.app.imagify.model.user.User

data class Collection(
    val id: String,
    val title: String,
    val description: String?,
    @SerializedName("published_at") val publishedAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val curated: Boolean?,
    val featured: Boolean?,
    @SerializedName("total_photos") val totalPhotos: Int,
    val private: Boolean?,
    @SerializedName("share_key") val shareKey: String?,
    val tags: List<Tag>?,
    @SerializedName("cover_photo") val coverPhoto: Photo?,
    @SerializedName("preview_photos") val previewPhotos: List<Photo>?,
    val user: User?,
    val links: Links?
)

data class Links(
    val self: String,
    val html: String,
    val photos: String
)