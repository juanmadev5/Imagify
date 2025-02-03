package com.jmdev.app.imagify.model

import androidx.compose.runtime.Stable
import com.jmdev.app.imagify.utils.PhotoQuality

@Stable
data class QualityModel(
    val quality: Int,
    val qualityToApply: PhotoQuality,
)

@Stable
data class OrientationModel(
    val orientation: Int,
    val orientationToApply: String,
)

@Stable
data class DownloadQualityModel(
    val quality: Int,
    val downloadQuality: PhotoQuality,
)