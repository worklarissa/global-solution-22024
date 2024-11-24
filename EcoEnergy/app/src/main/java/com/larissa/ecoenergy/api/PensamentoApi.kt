package com.larissa.ecoenergy.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PensamentoApi {
    @GET("pensamentos")
    fun getPensamentos(): Call<List<Pensamento>>

    @POST("pensamentos")
    suspend fun criarPensamento(@Body pensamento: Map<String, String>): Response<Pensamento>

    @PUT("pensamentos/{id}")
    suspend fun atualizarPensamento(@Path("id") id: Int, @Body pensamento: Map<String, String>): Response<Any>

    @DELETE("pensamentos/{id}")
    suspend fun deletarPensamento(@Path("id") id: Int): Response<Unit>
}