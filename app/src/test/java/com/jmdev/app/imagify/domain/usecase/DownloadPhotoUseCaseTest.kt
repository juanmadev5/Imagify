package com.jmdev.app.imagify.domain.usecase

import android.content.Context
import com.jmdev.app.imagify.utils.PhotoDownloadManager
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class DownloadPhotoUseCaseTest {

    private val downloadManager = mock(PhotoDownloadManager::class.java)
    private val context = mock(Context::class.java)
    private lateinit var useCase: DownloadPhotoUseCase

    @Before
    fun setUp() {
        useCase = DownloadPhotoUseCase(downloadManager)
    }

    @Test
    fun `returns true when download manager succeeds`() = runTest {
        `when`(downloadManager.downloadPhoto(context, "https://example.com/photo.jpg", "photo-1"))
            .thenReturn(true)

        val result = useCase(context, "https://example.com/photo.jpg", "photo-1")

        assertTrue(result)
    }

    @Test
    fun `returns false when download manager fails`() = runTest {
        `when`(downloadManager.downloadPhoto(context, "https://example.com/photo.jpg", "photo-1"))
            .thenReturn(false)

        val result = useCase(context, "https://example.com/photo.jpg", "photo-1")

        assertFalse(result)
    }
}
