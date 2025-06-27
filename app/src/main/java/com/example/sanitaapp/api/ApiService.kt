package com.example.sanitaapp.api

import com.example.sanitaapp.model.Planta
import com.example.sanitaapp.model.Noticia
import com.example.sanitaapp.model.ResumenItem
import com.example.sanitaapp.model.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field

interface ApiService {

    // Obtener todas las plantas (productos)
    @GET("api/plantas")
    suspend fun getPlantas(): List<Planta>

    // Obtener una planta por ID
    @GET("api/plantas/{id}")
    suspend fun getPlantaPorId(@Path("id") id: Int): Planta

    // Obtener resumen de inicio (noticias y publicaciones)
    @GET("api/resumen-inicio")
    suspend fun getResumenInicio(): List<ResumenItem>

    // Obtener todas las noticias
    @GET("api/noticias")
    suspend fun getNoticias(): List<Noticia>

    // Obtener noticia por ID
    @GET("api/noticia/{id}")
    suspend fun getNoticiaPorId(@Path("id") id: Int): Noticia

    // Buscar planta por nombre
    @GET("api/informacion")
    suspend fun buscarPlantaPorNombre(@Query("nombre") nombre: String): Planta

    // Login (POST)
    @FormUrlEncoded
    @POST("login")
    suspend fun loginUsuario(
        @Field("correo") correo: String,
        @Field("contrasena") contrasena: String
    ): LoginResponse
}
