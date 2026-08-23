package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.model.photo.Urls
import com.jmdev.app.imagify.model.unsplashphoto.Links
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.model.unsplashphoto.User
import com.jmdev.app.imagify.model.unsplashphoto.UserLinks
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetPhotoUseCaseTest {

    private val repository = mock(PhotoRepository::class.java)
    private lateinit var useCase: GetPhotoUseCase

    @Before
    fun setUp() {
        useCase = GetPhotoUseCase(repository)
    }

    @Test
    fun `returns photo from repository when found`() = runTest {
        val photo = samplePhoto(id = "abc123")
        `when`(repository.getPhoto("abc123")).thenReturn(photo)

        val result = useCase("abc123")

        assertEquals(photo, result)
    }

    @Test
    fun `returns null when repository has no photo for id`() = runTest {
        `when`(repository.getPhoto("missing")).thenReturn(null)

        val result = useCase("missing")

        assertNull(result)
    }

    private fun samplePhoto(id: String) = Photo(
        id = id,
        width = 1080,
        height = 1920,
        color = "#0c4040",
        downloads = 0,
        likes = 0,
        description = null,
        exif = null,
        location = null,
        urls = Urls(raw = "raw", full = "full", regular = "regular"),
        links = Links(self = "self", html = "html", download = "download", downloadLocation = "download_location"),
        user = User(
            id = "user-1",
            username = "juanma",
            name = "Juan Manuel",
            portfolioUrl = null,
            bio = null,
            location = null,
            links = UserLinks(self = "self", html = "html", photos = "photos", likes = "likes", portfolio = null),
        ),
    )
}
