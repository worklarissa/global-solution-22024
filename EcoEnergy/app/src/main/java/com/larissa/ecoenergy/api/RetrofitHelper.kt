package com.larissa.ecoenergy.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gs-2024-java.onrender.com/pensamentos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}