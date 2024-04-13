package com.jmdev.app.imagify.model

/*
data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("color") val color: String,
    @SerializedName("blur_hash") val blurHash: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("liked_by_user") val likedByUser: Boolean,
    @SerializedName("description") val description: String?,
    @SerializedName("user") val user: User,
    @SerializedName("current_user_collections") val currentUserCollections: List<Collection>,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("links") val links: Links
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("portfolio_url") val portfolioUrl: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("total_likes") val totalLikes: Int,
    @SerializedName("total_photos") val totalPhotos: Int,
    @SerializedName("total_collections") val totalCollections: Int,
    @SerializedName("instagram_username") val instagramUsername: String?,
    @SerializedName("twitter_username") val twitterUsername: String?,
    @SerializedName("profile_image") val profileImage: ProfileImage,
    @SerializedName("links") val links: UserLinks
)

data class ProfileImage(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String
)

data class UserLinks(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("photos") val photos: String,
    @SerializedName("likes") val likes: String,
    @SerializedName("portfolio") val portfolio: String
)

data class Collection(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("last_collected_at") val lastCollectedAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("cover_photo") val coverPhoto: Any?,
    @SerializedName("user") val user: Any?
)

data class Urls(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)

data class Links(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("download") val download: String,
    @SerializedName("download_location") val downloadLocation: String
)
*/