package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.ORIENTATION_PORTRAIT
import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SaveOrientationUseCaseTest {

    private val repository = mock(UserPreferencesRepository::class.java)
    private lateinit var useCase: SaveOrientationUseCase

    @Before
    fun setUp() {
        useCase = SaveOrientationUseCase(repository)
    }

    @Test
    fun `delegates orientation to repository`() = runTest {
        useCase(ORIENTATION_PORTRAIT)

        verify(repository).saveSearchOrientation(ORIENTATION_PORTRAIT)
    }
}
