package com.example.belanjayuk.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BelanjaYukMockApi {
    const val BASE_URL = "http://127.0.0.1:3001/api/"
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> getInstance(service: Class<T>): T{
        return retrofit.create(service)
    }
}
