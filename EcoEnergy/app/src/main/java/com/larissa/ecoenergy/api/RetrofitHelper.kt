package com.larissa.ecoenergy.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitHelper {
    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gs-2024-java.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


}