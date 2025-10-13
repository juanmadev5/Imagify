package com.jmdev.app.imagify.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jmdev.app.imagify.BASE_URL
import com.jmdev.app.imagify.DS_NAME
import com.jmdev.app.imagify.data.PhotoRepository
import com.jmdev.app.imagify.data.UserPreferences
import com.jmdev.app.imagify.network.AuthInterceptor
import com.jmdev.app.imagify.network.UnsplashAPI
import com.jmdev.app.imagify.presentation.screens.imageDetailScreen.ImageDetailViewModel
import com.jmdev.app.imagify.presentation.screens.mainScreen.MainScreenViewModel
import com.jmdev.app.imagify.presentation.screens.settingsScreen.SettingsViewModel
import com.jmdev.app.imagify.utils.PermissionManager
import com.jmdev.app.imagify.utils.PhotoDownloadManager
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Interceptor
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    // DataStore
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile(DS_NAME)
            }
        )
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API
    single<UnsplashAPI> {
        get<Retrofit>().create(UnsplashAPI::class.java)
    }

    single { PermissionManager() }

    single {
        PhotoRepository(
            service = get()
        )
    }

    single {
        UserPreferences(
            dataStore = get()
        )
    }

    single { PhotoDownloadManager() }

    viewModel {
        MainScreenViewModel(
            photoRepository = get(),
            userPreferences = get()
        )
    }

    viewModel {
        ImageDetailViewModel(
            photoRepository = get(),
            downloadManager = get(),
            permissionManager = get()
        )
    }

    viewModel {
        SettingsViewModel(userPreferences = get())
    }
}
