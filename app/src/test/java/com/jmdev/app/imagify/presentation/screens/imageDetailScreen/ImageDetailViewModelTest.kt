package com.jmdev.app.imagify.presentation.screens.imageDetailScreen

import app.cash.turbine.test
import com.jmdev.app.imagify.domain.usecase.DownloadPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetUserProfileUseCase
import com.jmdev.app.imagify.model.photo.User
import com.jmdev.app.imagify.model.photo.Urls
import com.jmdev.app.imagify.model.unsplashphoto.Links
import com.jmdev.app.imagify.model.unsplashphoto.Photo
import com.jmdev.app.imagify.model.unsplashphoto.User as UnsplashUser
import com.jmdev.app.imagify.model.unsplashphoto.UserLinks
import com.jmdev.app.imagify.utils.ConnectivityObserver
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class ImageDetailViewModelTest {

    private val getPhotoUseCase = mock(GetPhotoUseCase::class.java)
    private val getUserProfileUseCase = mock(GetUserProfileUseCase::class.java)
    private val downloadPhotoUseCase = mock(DownloadPhotoUseCase::class.java)
    private val connectivityObserver = mock(ConnectivityObserver::class.java)

    private lateinit var viewModel: ImageDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        `when`(connectivityObserver.isOnline).thenReturn(MutableStateFlow(true))
        viewModel = ImageDetailViewModel(
            getPhotoUseCase, getUserProfileUseCase, downloadPhotoUseCase, connectivityObserver,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun fakePhoto(id: String = "photo-1") = Photo(
        id = id,
        width = 800,
        height = 600,
        color = "#000000",
        downloads = 0,
        likes = 0,
        description = null,
        exif = null,
        location = null,
        urls = Urls(raw = "raw", full = "full", regular = "regular"),
        links = Links(self = "self", html = "html", download = "download", downloadLocation = "download_location"),
        user = UnsplashUser(
            id = "user-1",
            username = "author",
            name = "Author Name",
            portfolioUrl = null,
            bio = null,
            location = null,
            links = UserLinks(self = "self", html = "html", photos = "photos", likes = "likes", portfolio = null),
        ),
    )

    @Test
    fun `loadPhoto emits Success with the fetched photo and user profile`() = runTest {
        val photo = fakePhoto()
        val userProfile = User(id = "user-1", username = "author", name = "Author Name")
        `when`(getPhotoUseCase("photo-1")).thenReturn(photo)
        `when`(getUserProfileUseCase("author")).thenReturn(userProfile)

        viewModel.uiState.test {
            assertEquals(ImageDetailUiState.Loading, awaitItem())

            viewModel.loadPhoto("photo-1")

            val state = awaitItem()
            assertTrue(state is ImageDetailUiState.Success)
            state as ImageDetailUiState.Success
            assertEquals(photo, state.photo)
            assertEquals(userProfile, state.userProfile)
        }
    }

    @Test
    fun `loadPhoto emits network Error when the photo fetch throws IOException while offline`() = runTest {
        `when`(connectivityObserver.isOnline).thenReturn(MutableStateFlow(false))
        `when`(getPhotoUseCase("photo-1")).thenAnswer { throw IOException("no network") }
        viewModel = ImageDetailViewModel(
            getPhotoUseCase, getUserProfileUseCase, downloadPhotoUseCase, connectivityObserver,
        )

        viewModel.uiState.test {
            assertEquals(ImageDetailUiState.Loading, awaitItem())

            viewModel.loadPhoto("photo-1")

            assertEquals(ImageDetailUiState.Error(isNetworkError = true), awaitItem())
        }
    }

    @Test
    fun `loadPhoto emits non-network Error when the photo is not found`() = runTest {
        `when`(getPhotoUseCase("missing")).thenReturn(null)

        viewModel.uiState.test {
            assertEquals(ImageDetailUiState.Loading, awaitItem())

            viewModel.loadPhoto("missing")

            assertEquals(ImageDetailUiState.Error(isNetworkError = false), awaitItem())
        }
    }

    @Test
    fun `DownloadPhoto event downloads through the use case then returns the status to IDLE`() = runTest {
        val context = mock(android.content.Context::class.java)
        `when`(downloadPhotoUseCase(context, "link", "file")).thenReturn(true)

        viewModel.downloadStatus.test {
            assertEquals(DownloadStatus.IDLE, awaitItem())

            viewModel.onEvent(ImageDetailUiEvent.DownloadPhoto(context, "link", "file"))

            // The mocked use case resolves synchronously, so LOADING is superseded before this
            // StateFlow collector gets scheduled; SUCCESS and the later IDLE reset remain observable.
            assertEquals(DownloadStatus.SUCCESS, awaitItem())
            assertEquals(DownloadStatus.IDLE, awaitItem())
        }
    }
}
