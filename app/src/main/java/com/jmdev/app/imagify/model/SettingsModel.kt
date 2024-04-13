package com.jmdev.app.imagify.model

import com.jmdev.app.imagify.core.GetPhotosMode
import com.jmdev.app.imagify.core.Orientation
import com.jmdev.app.imagify.core.PhotoQuality

data class QualityModel(
    val quality: Int,
    val qualityToApply: PhotoQuality
)

data class OrientationModel(
    val orientation: Int,
    val orientationToApply: Orientation
)

data class OrderModel(
    val order: Int,
    val orderByToApply: String
)

data class DownloadQualityModel(
    val quality: Int,
    val downloadQuality: PhotoQuality
)

data class GetPhotosModeModel(
    val mode: Int,
    val getPhotosMode: GetPhotosMode
)