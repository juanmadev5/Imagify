package com.jmdev.app.imagify.presentation.screens.settingsScreen

import com.jmdev.app.imagify.utils.PhotoQuality

sealed class SettingsUiEvent {
    data class SetQuality(val quality: PhotoQuality) : SettingsUiEvent()
    data class SetOrientation(val orientation: String) : SettingsUiEvent()
}
