# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Context imports

@/home/juanma/dev-context-system/01-mobile/jetpack-compose/INDEX.md
@/home/juanma/dev-context-system/00-global/git-conventions.md

## Project

Imagify is a native Android app (Kotlin, Jetpack Compose) for browsing, searching, and downloading photos from the Unsplash API. Single Gradle module (`:app`), package `com.jmdev.app.imagify`.

## Build & test commands

```sh
./gradlew assembleDebug          # build debug APK
./gradlew testDebugUnitTest      # run JVM unit tests (app/src/test)
./gradlew connectedAndroidTest   # run instrumented tests (app/src/androidTest), needs a device/emulator
./gradlew testDebugUnitTest --tests "com.jmdev.app.imagify.domain.usecase.GetPhotoUseCaseTest"  # single test class
```

There is no separate lint/ktlint task configured beyond the default `./gradlew lint`.

### Required local setup before building
- `app/src/main/java/com/jmdev/app/imagify/Constants.kt` — `UNSPLASH_API_KEY` must be set to a real Unsplash API key.
- `app/google-services.json` — Firebase config, not checked into the repo (see `.gitignore`); needed because Crashlytics/Analytics/Perf plugins are applied unconditionally in `app/build.gradle.kts`. Register the app under namespace `com.jmdev.app.imagify` in Firebase Console and drop the generated file in place.

## Architecture

Clean architecture with three layers, wired together by Koin (no Hilt/Dagger):

- **`data/`** — `remote/` has the Retrofit `UnsplashAPI` interface and three `OkHttp` interceptors (`AuthInterceptor` injects the API key, `NetworkCacheInterceptor`/`OfflineCacheInterceptor` implement online/offline HTTP caching via a shared `okhttp3.Cache`). `paging/` has `PagingSource` implementations (`HomePagingSource`, `SearchPagingSource`) that back Paging 3 flows. `repository/` implements the domain repository interfaces on top of the API/paging sources.
- **`domain/`** — `repository/` defines repository contracts (`PhotoRepository`, `UserPreferencesRepository`) consumed by the data layer. `usecase/` holds single-responsibility use cases, each a class with an `operator fun invoke(...)` that delegates to a repository (or, for `DownloadPhotoUseCase`, to `PhotoDownloadManager`).
- **`presentation/`** — Compose UI. `screens/<name>/` packages follow a consistent MVI-ish pattern per screen: `<Screen>.kt` (composable), `<Screen>ViewModel.kt`, `<Screen>UiState.kt`, `<Screen>UiEvent.kt`. `components/` has shared composables. `navigation/` uses Navigation 3 (`androidx.navigation3`), not the older Navigation Compose — routes are `@Serializable` `NavKey` objects/data classes in `NavigationRoutes.kt`, and `NavigationController.kt` wires them via `entryProvider`/`NavDisplay`. `theme/` is standard Material 3 theme setup.
- **`di/AppModule.kt`** — single Koin module (`appModule`) declaring all singles, use cases, and `viewModel { }` bindings. Any new repository, use case, or ViewModel must be registered here or `AppTest`'s `appModule.verify()` check (and DI resolution at runtime) will fail.
- **`model/`** — `model/unsplashphoto/` mirrors the raw Unsplash API response shape (`Photo`, `User`, `Exif`, `Location`, etc.); `model/photo/` holds slimmer domain-facing models (`FeedPhoto`, `User`, `Urls`) used by paging/UI. Don't conflate the two `User`/model sets — they live in different packages for a reason.
- **`utils/`** — cross-cutting helpers: `CoilImageBuilder.buildImageRequest` (centralizes Coil cache-policy config — covered by `AppTest`), `PhotoDownloadManager`, `ConnectivityObserver`, `BlurHashDecoder`, `PhotoQuality`.

### Key libraries
Compose + Material 3, Koin (DI), Retrofit + OkHttp + Gson, Coil 2 (images, BlurHash placeholders), Paging 3 (+ Paging Compose), DataStore Preferences (user settings), Navigation 3, Firebase (Crashlytics/Analytics/Performance), kotlinx.serialization.

### Testing conventions
- Unit tests live in `app/src/test/java/com/jmdev/app/imagify/`, mirroring `domain/usecase/` for use-case tests, plus `AppTest.kt` at the root which verifies the Koin graph (`appModule.verify()`) and asserts Coil cache-policy behavior of `buildImageRequest`.
- Use cases are tested by mocking their repository/manager dependency with Mockito and asserting the `invoke()` delegates correctly.
- `AppTest` runs with `UnconfinedTestDispatcher` and a mocked `Application`/`Context` via `startKoin`/`stopKoin` in `@Before`/`@After` — follow this pattern for any new test that needs the DI graph.
