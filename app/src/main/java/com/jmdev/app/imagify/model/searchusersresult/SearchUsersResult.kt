package com.jmdev.app.imagify.model.searchusersresult

import com.google.gson.annotations.SerializedName
import com.jmdev.app.imagify.model.user.User

data class SearchUsersResult(
    val total: Int,
    @SerializedName("total_pages") val total_pages: Int,
    val results: List<User>
)