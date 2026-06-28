package com.jmdev.app.imagify.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jmdev.app.imagify.BASE_URL
import com.jmdev.app.imagify.DS_NAME
import com.jmdev.app.imagify.data.remote.AuthInterceptor
import com.jmdev.app.imagify.data.remote.NetworkCacheInterceptor
import com.jmdev.app.imagify.data.remote.OfflineCacheInterceptor
import com.jmdev.app.imagify.data.remote.UnsplashAPI
import com.jmdev.app.imagify.data.repository.PhotoRepositoryImpl
import com.jmdev.app.imagify.data.repository.UserPreferencesRepositoryImpl
import com.jmdev.app.imagify.domain.repository.PhotoRepository
import com.jmdev.app.imagify.domain.repository.UserPreferencesRepository
import com.jmdev.app.imagify.domain.usecase.DownloadPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetPhotoUseCase
import com.jmdev.app.imagify.domain.usecase.GetUserPreferencesUseCase
import com.jmdev.app.imagify.domain.usecase.GetUserProfileUseCase
import com.jmdev.app.imagify.domain.usecase.SaveOrientationUseCase
import com.jmdev.app.imagify.domain.usecase.SaveQualityUseCase
import com.jmdev.app.imagify.presentation.screens.imageDetailScreen.ImageDetailViewModel
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreenViewModel
import com.jmdev.app.imagify.presentation.screens.settingsScreen.SettingsViewModel
import com.jmdev.app.imagify.utils.ConnectivityObserver
import com.jmdev.app.imagify.utils.PhotoDownloadManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { ConnectivityObserver(androidContext()) }

    single {
        val cache = Cache(androidContext().cacheDir, 10L * 1024 * 1024)
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(OfflineCacheInterceptor(get()))
            .addNetworkInterceptor(NetworkCacheInterceptor())
            .build()
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile(DS_NAME) }
        )
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<UnsplashAPI> { get<Retrofit>().create(UnsplashAPI::class.java) }

    single<PhotoRepository> { PhotoRepositoryImpl(service = get()) }

    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(dataStore = get()) }

    single { PhotoDownloadManager() }

    // Use cases
    single { GetPhotoUseCase(repository = get()) }
    single { GetUserProfileUseCase(repository = get()) }
    single { GetUserPreferencesUseCase(repository = get()) }
    single { SaveQualityUseCase(repository = get()) }
    single { SaveOrientationUseCase(repository = get()) }
    single { DownloadPhotoUseCase(downloadManager = get()) }

    viewModel {
        MainScreenViewModel(
            photoRepository = get(),
            getUserPreferencesUseCase = get(),
            connectivityObserver = get(),
        )
    }

    viewModel {
        ImageDetailViewModel(
            getPhotoUseCase = get(),
            getUserProfileUseCase = get(),
            downloadPhotoUseCase = get(),
            connectivityObserver = get(),
        )
    }

    viewModel {
        SettingsViewModel(
            getUserPreferencesUseCase = get(),
            saveQualityUseCase = get(),
            saveOrientationUseCase = get()
        )
    }
}
