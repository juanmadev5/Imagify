package com.jmdev.app.imagify.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class NetworkCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", "public, max-age=${5 * 60}")
            .build()
    }
}
