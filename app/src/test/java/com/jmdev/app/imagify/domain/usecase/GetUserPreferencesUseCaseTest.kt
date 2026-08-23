package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.ORIENTATION_LANDSCAPE
import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetUserPreferencesUseCaseTest {

    private val repository = mock(UserPreferencesRepository::class.java)
    private lateinit var useCase: GetUserPreferencesUseCase

    @Before
    fun setUp() {
        useCase = GetUserPreferencesUseCase(repository)
    }

    @Test
    fun `combines quality and orientation from repository into a pair`() = runTest {
        `when`(repository.getPhotoQuality()).thenReturn(PhotoQuality.FULL)
        `when`(repository.getSearchOrientation()).thenReturn(ORIENTATION_LANDSCAPE)

        val result = useCase()

        assertEquals(PhotoQuality.FULL to ORIENTATION_LANDSCAPE, result)
    }
}
