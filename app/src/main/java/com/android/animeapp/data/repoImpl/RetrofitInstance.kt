package com.android.animeapp.data.repoImpl

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor((HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })).connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    const val base_Url = "http://dragonball-api.com/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(base_Url).client(client).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AnimeApiService by lazy {retrofit.create(AnimeApiService::class.java)}

}