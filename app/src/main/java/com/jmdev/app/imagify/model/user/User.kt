package com.jmdev.app.imagify.model.user

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.collection.Links
import com.jmdev.app.imagify.model.photo.Photo

data class User(
    val id: String,
    @SerializedName("updated_at") val updatedAt: String?,
    val username: String?,
    val name: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("instagram_username") val instagramUsername: String?,
    @SerializedName("twitter_username") val twitterUsername: String?,
    @SerializedName("portfolio_url") val portfolioUrl: String?,
    val bio: String?,
    val location: String?,
    @SerializedName("total_likes") val totalLikes: Int?,
    @SerializedName("total_photos") val totalPhotos: Int?,
    @SerializedName("total_collections") val totalCollections: Int?,
    @SerializedName("followed_by_user") val followedByUser: Boolean?,
    @SerializedName("followers_count") val followersCount: Int?,
    @SerializedName("following_count") val followingCount: Int?,
    val downloads: Int?,
    @SerializedName("profile_image") val profileImage: ProfileImage?,
    val badge: Badge?,
    val links: Links?,
    val photos: List<Photo>?
)

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

data class Badge(
    val title: String?,
    val primary: Boolean?,
    val slug: String?,
    val link: String?
)

data class Links(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String,
    val following: String,
    val followers: String
)