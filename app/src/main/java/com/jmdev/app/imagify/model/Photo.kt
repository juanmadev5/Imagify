package com.jmdev.app.imagify.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("blur_hash") val blurHash: String? = null,
    @SerializedName("likes") val likes: Int? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("user") val user: User? = null,
)

data class Urls(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
)