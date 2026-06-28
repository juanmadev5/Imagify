package com.jmdev.app.imagify.data.remote

import com.jmdev.app.imagify.utils.ConnectivityObserver
import okhttp3.Interceptor
import okhttp3.Response

class OfflineCacheInterceptor(private val connectivityObserver: ConnectivityObserver) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (!connectivityObserver.isOnline.value) {
            chain.request().newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=${7 * 24 * 60 * 60}")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}
