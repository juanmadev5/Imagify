package com.jmdev.app.imagify.model.searchcollectionsresult

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.collection.Collection

data class SearchCollectionsResult(
    val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<Collection>
)