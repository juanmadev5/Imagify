package com.jmdev.app.imagify.presentation.screens.settingsScreen

import app.cash.turbine.test
import com.jmdev.app.imagify.ORIENTATION_LANDSCAPE
import com.jmdev.app.imagify.ORIENTATION_PORTRAIT
import com.jmdev.app.imagify.domain.usecase.GetUserPreferencesUseCase
import com.jmdev.app.imagify.domain.usecase.SaveOrientationUseCase
import com.jmdev.app.imagify.domain.usecase.SaveQualityUseCase
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val getUserPreferencesUseCase = mock(GetUserPreferencesUseCase::class.java)
    private val saveQualityUseCase = mock(SaveQualityUseCase::class.java)
    private val saveOrientationUseCase = mock(SaveOrientationUseCase::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun buildViewModel(): SettingsViewModel {
        `when`(getUserPreferencesUseCase()).thenReturn(PhotoQuality.REGULAR to ORIENTATION_LANDSCAPE)
        return SettingsViewModel(getUserPreferencesUseCase, saveQualityUseCase, saveOrientationUseCase)
    }

    @Test
    fun `loads persisted preferences into ui state on init`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(PhotoQuality.REGULAR, state.photoQuality)
            assertEquals(ORIENTATION_LANDSCAPE, state.searchPhotoOrientation)
        }
    }

    @Test
    fun `SetQuality event updates ui state immediately and persists through the use case`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() // initial state loaded from preferences

            viewModel.onEvent(SettingsUiEvent.SetQuality(PhotoQuality.FULL))

            assertEquals(PhotoQuality.FULL, awaitItem().photoQuality)
        }
        verify(saveQualityUseCase, timeout(1000)).invoke(PhotoQuality.FULL)
    }

    @Test
    fun `SetOrientation event updates ui state immediately and persists through the use case`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() // initial state loaded from preferences

            viewModel.onEvent(SettingsUiEvent.SetOrientation(ORIENTATION_PORTRAIT))

            assertEquals(ORIENTATION_PORTRAIT, awaitItem().searchPhotoOrientation)
        }
        verify(saveOrientationUseCase, timeout(1000)).invoke(ORIENTATION_PORTRAIT)
    }
}
