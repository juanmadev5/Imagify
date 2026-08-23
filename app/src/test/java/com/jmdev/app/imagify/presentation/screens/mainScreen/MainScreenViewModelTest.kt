package com.jmdev.app.imagify.presentation.screens.mainScreen

import androidx.paging.PagingData
import app.cash.turbine.test
import com.jmdev.app.imagify.ORIENTATION_LANDSCAPE
import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.domain.usecase.GetUserPreferencesUseCase
import com.jmdev.app.imagify.utils.ConnectivityObserver
import com.jmdev.app.imagify.utils.PhotoQuality
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest {

    private val photoRepository = mock(PhotoRepository::class.java)
    private val getUserPreferencesUseCase = mock(GetUserPreferencesUseCase::class.java)
    private val connectivityObserver = mock(ConnectivityObserver::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        `when`(photoRepository.getFeedPhotos()).thenReturn(flowOf(PagingData.empty()))
        `when`(photoRepository.searchPhotos(anyString(), anyString())).thenReturn(flowOf(PagingData.empty()))
        `when`(connectivityObserver.isOnline).thenReturn(MutableStateFlow(true))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun buildViewModel(): MainScreenViewModel {
        `when`(getUserPreferencesUseCase()).thenReturn(PhotoQuality.FULL to ORIENTATION_LANDSCAPE)
        return MainScreenViewModel(photoRepository, getUserPreferencesUseCase, connectivityObserver)
    }

    @Test
    fun `loads persisted preferences into ui state on init`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(PhotoQuality.FULL, state.photoQuality)
            assertEquals(ORIENTATION_LANDSCAPE, state.searchPhotoOrientation)
        }
    }

    @Test
    fun `QueryPhotos event updates the search query in ui state`() = runTest {
        val viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() // initial state loaded from preferences

            viewModel.onEvent(MainScreenUiEvent.QueryPhotos("mountains"))

            assertEquals("mountains", awaitItem().searchQuery)
        }
    }

    @Test
    fun `isOnline reflects the connectivity observer state`() = runTest {
        val viewModel = buildViewModel()

        assertTrue(viewModel.isOnline.value)
    }
}
