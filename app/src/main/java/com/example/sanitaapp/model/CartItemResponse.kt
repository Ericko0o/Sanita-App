package com.example.sanitaapp.model

data class CartItemResponse(
    val id: Int,
    val usuarioId: Int,
    val plantaId: Int,
    val cantidad: Int,
    val plantaNombre: String,
    val plantaImagen: String,
    val plantaPrecio: Double
)
