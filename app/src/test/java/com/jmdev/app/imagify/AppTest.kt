package com.jmdev.app.imagify

import android.app.Application
import coil.ImageLoader
import coil.request.CachePolicy
import com.jmdev.app.imagify.module.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.verify.verify
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class AppTest : KoinTest {

    private lateinit var imageLoader: ImageLoader
    private val app: App = mock(App::class.java)
    private val cacheDir = File("cache")

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        startKoin {
            androidContext(mock(Application::class.java))
            modules(appModule)
        }

        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        `when`(app.cacheDir).thenReturn(cacheDir)
        `when`(app.applicationContext).thenReturn(app)
        `when`(app.newImageLoader()).thenCallRealMethod()

        imageLoader = app.newImageLoader()
    }

    @After
    fun tearDown() {
        stopKoin()
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }
        Dispatchers.resetMain()
    }

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `check koin modules`() {
        appModule.verify()
    }

    @Test
    fun `image loader has memory cache enabled`() {
        assertEquals(CachePolicy.ENABLED, imageLoader.defaults.memoryCachePolicy)
    }

    @Test
    fun `image loader has disk cache enabled`() {
        assertEquals(CachePolicy.ENABLED, imageLoader.defaults.diskCachePolicy)
    }

    @Test
    fun `image loader has a non-null memory cache`() {
        assertNotNull(imageLoader.memoryCache)
    }

    @Test
    fun `image loader has a non-null disk cache`() {
        assertNotNull(imageLoader.diskCache)
    }
}
