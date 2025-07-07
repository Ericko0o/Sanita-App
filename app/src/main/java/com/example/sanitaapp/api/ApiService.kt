package com.example.sanitaapp.api

import com.example.sanitaapp.model.Planta
import com.example.sanitaapp.model.Noticia
import com.example.sanitaapp.model.ResumenItem
import com.example.sanitaapp.model.LoginResponse
import com.example.sanitaapp.model.Nindependiente
import com.example.sanitaapp.model.CartItemRequest
import com.example.sanitaapp.model.CartItemResponse
import com.example.sanitaapp.model.CartItemUpdateRequest
import com.example.sanitaapp.model.ApiResponse
import com.example.sanitaapp.model.CheckoutRequest
import com.example.sanitaapp.model.OrderResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Field

interface ApiService {

    // Obtener todas las plantas (productos)
    @GET("api/plantas")
    suspend fun getPlantas(): List<Planta>

    // Obtener resumen de inicio (noticias y publicaciones)
    @GET("api/resumen-inicio")
    suspend fun getResumenInicio(): List<ResumenItem>

    // Obtener todas las noticias
    @GET("api/noticias")
    suspend fun getNoticias(): List<Noticia>

    @GET("api/noticias")
    suspend fun obtenerNoticias(): List<Nindependiente>

    // Obtener noticia por ID
    @GET("api/noticia/{id}")
    suspend fun obtenerNoticiaPorId(@Path("id") id: Int): Nindependiente

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

    @GET("api/plantas/categoria/{id}")
    suspend fun getPlantasPorCategoria(@Path("id") categoriaId: Int): List<Planta>

    @GET("api/plantas/{id}")
    suspend fun getPlantaPorId(@Path("id") id: Int): Planta

    @POST("api/carrito")
    suspend fun addToCart(@Body item: CartItemRequest): ApiResponse

    @GET("api/carrito/{usuarioId}")
    suspend fun getCart(@Path("usuarioId") usuarioId: Int): List<CartItemResponse>

    @PUT("api/carrito/{usuarioId}/{plantaId}")
    suspend fun updateCartItem(
        @Path("usuarioId") userId: Int,
        @Path("plantaId") plantaId: Int,
        @Body request: CartItemUpdateRequest
    ): ApiResponse

    @DELETE("api/carrito/{usuarioId}/{plantaId}")
    suspend fun deleteCartItem(
        @Path("usuarioId") userId: Int,
        @Path("plantaId") plantaId: Int
    ): ApiResponse

    @DELETE("api/carrito/usuario/{usuarioId}")
    suspend fun clearCart(@Path("usuarioId") usuarioId: Int): ApiResponse

    @POST("api/pago")
    suspend fun checkout(@Body request: CheckoutRequest): ApiResponse

    @GET("api/pedidos/{usuarioId}")
    suspend fun getOrders(@Path("usuarioId") userId: Int): List<OrderResponse>

    @PUT("api/pedidos/{pedidoId}/recibido")
    suspend fun markOrderReceived(@Path("pedidoId") orderId: Int): ApiResponse

}
