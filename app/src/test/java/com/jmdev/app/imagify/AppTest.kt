package com.jmdev.app.imagify

import android.app.Application
import android.content.Context
import coil.request.CachePolicy
import com.jmdev.app.imagify.di.appModule
import com.jmdev.app.imagify.utils.buildImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class AppTest : KoinTest {

    private val context: Context = mock(Context::class.java)

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        startKoin {
            androidContext(mock(Application::class.java))
            modules(appModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `check koin modules`() {
        appModule.verify(extraTypes = listOf(Context::class))
    }

    @Test
    fun `image request has memory cache enabled`() {
        val request = buildImageRequest(context, data = "https://example.com/photo.jpg", cacheKey = "photo-1")
        assertEquals(CachePolicy.ENABLED, request.memoryCachePolicy)
    }

    @Test
    fun `image request has disk cache enabled`() {
        val request = buildImageRequest(context, data = "https://example.com/photo.jpg", cacheKey = "photo-1")
        assertEquals(CachePolicy.ENABLED, request.diskCachePolicy)
    }

    @Test
    fun `image request has network cache enabled`() {
        val request = buildImageRequest(context, data = "https://example.com/photo.jpg", cacheKey = "photo-1")
        assertEquals(CachePolicy.ENABLED, request.networkCachePolicy)
    }
}
