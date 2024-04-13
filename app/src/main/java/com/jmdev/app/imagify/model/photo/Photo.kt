package com.jmdev.app.imagify.model.photo

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.collection.Collection
import com.jmdev.app.imagify.model.collection.Links
import com.jmdev.app.imagify.model.statistics.PhotoStatistics
import com.jmdev.app.imagify.model.user.User

data class Photo(
    val id: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    val width: Int?,
    val height: Int?,
    val color: String? = "#E0E0E0",
    @SerializedName("blur_hash") val blurHash: String?,
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    @SerializedName("liked_by_user") var likedByUser: Boolean?,
    val description: String?,
    @SerializedName("alt_description") val altDescription: String?,
    val exif: Exif?,
    val location: Location?,
    val tags: List<Tag>?,
    @SerializedName("current_user_collections") val currentUserCollections: List<Collection>?,
    val sponsorship: Sponsorship?,
    val urls: Urls,
    val links: Links?,
    val user: User?,
    val statistics: PhotoStatistics?
) 


data class Exif(
    val make: String?,
    val model: String?,
    @SerializedName("exposure_time") val exposureTime: String?,
    val aperture: String?,
    @SerializedName("focal_length") val focalLength: String?,
    val iso: Int?
) 


data class Location(
    val city: String?,
    val country: String?,
    val position: Position?
) 


data class Position(
    val latitude: Double?,
    val longitude: Double?
) 


data class Tag(
    val type: String?,
    val title: String?
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


data class Sponsorship(
    val sponsor: User?
) 