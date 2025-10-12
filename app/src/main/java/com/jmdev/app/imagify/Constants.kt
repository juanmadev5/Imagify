package com.jmdev.app.imagify

import com.jmdev.app.imagify.utils.PhotoQuality

const val BASE_URL = "https://api.unsplash.com/"
const val UNSPLASH_API_KEY = "KEY"
const val ORIENTATION_PORTRAIT = "portrait"
const val ORIENTATION_LANDSCAPE = "landscape"
const val ORIENTATION_SQUARISH = "squarish"

const val DEFAULT_PHOTO_ORIENTATION = "squarish"

val DEFAULT_PHOTO_QUALITY = PhotoQuality.REGULAR

const val CROSSFADE_VALUE = 500

const val DS_NAME = "AppDataStore"

val SEARCH_KEYWORDS = listOf(
    "blue sky",
    "landscape",
    "sunset",
    "mountains",
    "ocean",
    "forest",
    "cityscape",
    "wildlife",
    "flowers",
    "architecture",
    "night sky",
    "abstract",
    "portrait",
    "waterfall",
    "desert",
    "snow",
    "beach",
    "clouds",
    "stars",
    "autumn"
)