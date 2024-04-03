package com.jmdev.app.imagify.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val id: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    @SerializedName("blur_hash") val blurHash: String,
    val downloads: Int,
    val likes: Int,
    @SerializedName("liked_by_user") val likedByUser: Boolean,
    val description: String?,
    val exif: Exif,
    val location: Location,
    @SerializedName("current_user_collections") val currentUserCollections: List<Collection>,
    val urls: Urls,
    val links: Links,
    val user: User
)

data class Exif(
    val make: String,
    val model: String,
    @SerializedName("exposure_time") val exposureTime: String,
    val aperture: String,
    @SerializedName("focal_length") val focalLength: String,
    val iso: Int
)

data class Location(
    val name: String,
    val city: String,
    val country: String,
    val position: Position
)

data class Position(
    val latitude: Double,
    val longitude: Double
)

data class Collection(
    val id: Int,
    val title: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("last_collected_at") val lastCollectedAt: String,
    @SerializedName("updated_at") val collectionUpdatedAt: String,
    @SerializedName("cover_photo") val coverPhoto: Any?, // Depending on your needs, you might want to create a CoverPhoto class
    val user: Any? // Depending on your needs, you might want to create a User class
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class Links(
    val self: String,
    val html: String,
    val download: String,
    @SerializedName("download_location") val downloadLocation: String
)

data class User(
    val id: String,
    @SerializedName("updated_at") val userUpdatedAt: String,
    val username: String,
    val name: String,
    @SerializedName("portfolio_url") val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes") val totalLikes: Int,
    @SerializedName("total_photos") val totalPhotos: Int,
    @SerializedName("total_collections") val totalCollections: Int,
    @SerializedName("instagram_username") val instagramUsername: String?,
    @SerializedName("twitter_username") val twitterUsername: String?,
    val links: UserLinks
)

data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String
)