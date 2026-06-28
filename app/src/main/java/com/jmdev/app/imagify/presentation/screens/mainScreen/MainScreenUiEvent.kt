package com.jmdev.app.imagify.presentation.screens.mainScreen

sealed class MainScreenUiEvent {
    data class QueryPhotos(val query: String) : MainScreenUiEvent()
}
