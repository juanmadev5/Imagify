package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.model.unsplashphoto.Photo

class GetPhotoUseCase(private val repository: PhotoRepository) {
    suspend operator fun invoke(id: String): Photo? = repository.getPhoto(id)
}
