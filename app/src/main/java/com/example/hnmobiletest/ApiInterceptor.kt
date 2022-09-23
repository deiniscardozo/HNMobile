package com.example.hnmobiletest

import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("content-type", "application/json").build()

        return chain.proceed(request)
    }

}
