package com.jmdev.app.imagify.model

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