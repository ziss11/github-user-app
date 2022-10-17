package com.example.fundamentalsubmission.utilities

import com.example.fundamentalsubmission.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Authorization", BuildConfig.API_TOKEN)
            .build()
        return chain.proceed(request)
    }
}