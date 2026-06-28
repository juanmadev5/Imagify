package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.model.photo.User

class GetUserProfileUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(username: String): User? = repository.getUserProfile(username)
}
