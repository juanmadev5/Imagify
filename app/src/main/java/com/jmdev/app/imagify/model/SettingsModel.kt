package com.jmdev.app.imagify.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.jmdev.app.imagify.utils.PhotoQuality

@Stable
data class QualityModel(
    val quality: Int,
    val qualityToApply: PhotoQuality,
)

data class OrientationModel(
    @param:StringRes val orientation: Int,
    val orientationToApply: String,
)
