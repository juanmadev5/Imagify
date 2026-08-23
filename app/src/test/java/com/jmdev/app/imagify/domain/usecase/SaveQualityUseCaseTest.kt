package com.jmdev.app.imagify.domain.usecase

import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SaveQualityUseCaseTest {

    private val repository = mock(UserPreferencesRepository::class.java)
    private lateinit var useCase: SaveQualityUseCase

    @Before
    fun setUp() {
        useCase = SaveQualityUseCase(repository)
    }

    @Test
    fun `delegates quality to repository`() = runTest {
        useCase(PhotoQuality.RAW)

        verify(repository).saveQuality(PhotoQuality.RAW)
    }
}
