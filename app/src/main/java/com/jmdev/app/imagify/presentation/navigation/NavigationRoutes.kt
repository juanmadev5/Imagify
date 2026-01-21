package com.jmdev.app.imagify.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Main : NavKey

@Serializable
data class Details(val id: String) : NavKey

@Serializable
data object Settings : NavKey
