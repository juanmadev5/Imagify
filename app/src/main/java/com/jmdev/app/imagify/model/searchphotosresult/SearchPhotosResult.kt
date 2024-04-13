package com.jmdev.app.imagify.model.searchphotosresult

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.photo.Photo

data class SearchPhotosResult(
    val total: Int,
    @SerializedName("total_pages") val total_pages: Int,
    val results: List<Photo>
)