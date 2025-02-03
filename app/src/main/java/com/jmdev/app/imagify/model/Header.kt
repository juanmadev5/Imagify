package com.jmdev.app.imagify.model

import com.jmdev.app.imagify.App

sealed class Header(val name: String, val value: String) {
    data object VersionHeader : Header("Accept-Version", "v1")
    data object AuthorizationHeader : Header("Authorization", "Client-ID ${App.API_KEY}")
}