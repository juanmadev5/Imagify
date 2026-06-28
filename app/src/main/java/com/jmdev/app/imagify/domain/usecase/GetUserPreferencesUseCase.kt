package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.utils.PhotoQuality

class GetUserPreferencesUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(): Pair<PhotoQuality, String> =
        Pair(repository.getPhotoQuality(), repository.getSearchOrientation())
}
