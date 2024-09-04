package com.jmdev.app.imagify.model

import com.google.gson.annotations.SerializedName

data class SearchPhotosResult(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<Photo>
)