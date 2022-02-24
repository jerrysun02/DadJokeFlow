package com.langlab.dadjokeflow.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

object RemoteApi {
    private const val API_BASE_URL = "https://icanhazdadjoke.com/"
    private var serviceApiInterface: ServiceApiInterface? = null

    fun build(): ServiceApiInterface? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        serviceApiInterface = retrofit.create(
            ServiceApiInterface::class.java
        )
        return serviceApiInterface as ServiceApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServiceApiInterface {
        @Headers("Accept: application/json")
        @GET("/")
        suspend fun fetchJoke(): Response<JokeResponse>
    }
}