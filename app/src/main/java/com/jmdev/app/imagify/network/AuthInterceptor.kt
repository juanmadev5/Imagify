package com.jmdev.app.imagify.network

import com.jmdev.app.imagify.model.Header
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(Header.VersionHeader.name, Header.VersionHeader.value)
            .addHeader(Header.AuthorizationHeader.name, Header.AuthorizationHeader.value)
            .build()
        return chain.proceed(request)
    }
}