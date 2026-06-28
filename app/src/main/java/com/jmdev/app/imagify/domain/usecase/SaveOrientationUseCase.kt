package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository

class SaveOrientationUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(orientation: String) = repository.saveSearchOrientation(orientation)
}
