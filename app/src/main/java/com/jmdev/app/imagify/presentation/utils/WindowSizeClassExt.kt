package com.jmdev.app.imagify.presentation.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

private const val COMPACT_GRID_COLUMNS = 1
private const val MEDIUM_GRID_COLUMNS = 2
private const val EXPANDED_GRID_COLUMNS = 3

/**
 * Number of feed grid columns for the current window width, following Material 3's
 * compact/medium/expanded breakpoints (phones vs. tablets/foldables in portrait vs. landscape).
 */
@Composable
fun photoGridColumnCount(): Int {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    return when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> EXPANDED_GRID_COLUMNS
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> MEDIUM_GRID_COLUMNS
        else -> COMPACT_GRID_COLUMNS
    }
}

@Composable
fun isExpandedWidthWindow(): Boolean {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    return windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
}
