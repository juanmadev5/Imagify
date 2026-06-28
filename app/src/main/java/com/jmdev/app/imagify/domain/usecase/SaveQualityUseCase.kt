package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.utils.PhotoQuality

class SaveQualityUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(quality: PhotoQuality) = repository.saveQuality(quality)
}
