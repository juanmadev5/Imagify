package com.jmdev.app.imagify.network

import com.jmdev.app.imagify.App
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", "Client-ID ${App.API_KEY}")
            .build()
        return chain.proceed(request)
    }
}