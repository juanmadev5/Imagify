package com.jmdev.app.imagify.model

import com.jmdev.app.imagify.UNSPLASH_API_KEY

sealed class Header(val name: String, val value: String) {
    data object VersionHeader : Header("Accept-Version", "v1")
    data object AuthorizationHeader : Header("Authorization", "Client-ID $UNSPLASH_API_KEY")
}